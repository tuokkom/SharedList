package com.tuokko.sharedlist.controllers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuokko.sharedlist.models.AppPreferences
import com.tuokko.sharedlist.models.Navigator
import com.tuokko.sharedlist.views.listview.ItemListAdapter
import com.tuokko.sharedlist.views.listview.ListView

class MainListActivity : BaseActivity(), ListView.Listener {

    companion object {
        private const val TAG = "MainActivity"
        fun start(context: Context) {
            val intent = Intent(context, MainListActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var view: ListView
    private lateinit var prefs: AppPreferences
    private lateinit var navigator: Navigator
    private var listId: String? = null
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = compositionRoot.mainListView
        prefs = compositionRoot.appPreferences
        navigator = compositionRoot.navigator

        listId = prefs.getListId()

        if (listId.isNullOrEmpty()) {
            navigator.navigateToChangeListActivity()
        }

        updateListFromDatabase()

        setContentView(view.getRootView())
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
    }

    private fun addItemToList(item: String) {
        Log.d(TAG, "Adding item to recyclerView $item")
        if (listId == null) return
        val listItemProperties = hashMapOf(
            "checked" to false
        )
        db.collection(listId!!).document(item).set(listItemProperties)
            .addOnSuccessListener {
                updateListFromDatabase()
            }
            .addOnFailureListener {
                Log.d(TAG, "Adding the item: $item to list: $listId failed, error ${it.message}")
            }
    }

    private fun updateListFromDatabase() {
        if (listId == null) return


        db.collection(listId!!).get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemListAdapter.SingleItem>()
                for (item in result) {
                    Log.d(TAG, "Received data from database, item: ${item.id}")
                    Log.d(TAG, "Received data from database, more info: ${item.data}")
                    val checkState: Boolean = item.data["checked"] as Boolean
                    itemList.add(ItemListAdapter.SingleItem(item.id, checkState))
                }
                view.updateListItems(itemList)
        }


    }

    private fun deleteSelectedItems() {
        if (listId == null) return
        db.collection(listId!!).get()
                .addOnSuccessListener { result ->
                    for (item in result) {
                        val checkState: Boolean = item.data["checked"] as Boolean
                        if (checkState) {
                            Log.d(TAG, "Deleting item: ${item.id}")
                            deleteSingleItem(item.id)
                        }
                    }
                }
    }

    private fun deleteSingleItem(itemName: String) {
        if (listId == null) return
        db.collection(listId!!).document(itemName)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "deleteSingleItem() Item: $itemName deleted")
                    //itemListAdapter.deleteItemFromList(itemName)
                    view.deleteListItem(itemName)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to delete item: $itemName, error: ${it.message}")
                }
    }

    override fun onItemClicked(item: ItemListAdapter.SingleItem) {
        Log.d(TAG, "onItemClicked() Item name: ${item.name}")
        db.collection(listId!!).document(item.name).update("checked", item.checked)
            .addOnSuccessListener {
                Log.d(TAG, "onItemClicked() Item updated to state: ${item.checked}")
                view.updateListItem(item)
            }
            .addOnFailureListener {
                Log.e(TAG, "onItemClicked() Item update failed")
            }
    }

    override fun onItemAdded(item: ItemListAdapter.SingleItem) {
        Log.d(TAG, "onItemAdded() Item name: ${item.name}")
        if (item.name.isEmpty()) {
            Log.d(TAG, "onItemAdded() Item to be added is empty.")
            return
        }
        addItemToList(item.name)
    }

    override fun onDeleteItemsClicked() {
        Log.d(TAG, "onDeleteItemsClicked() Called")
        deleteSelectedItems()
    }

    override fun onChangeListClicked() {
        navigator.navigateToChangeListActivity()
    }
}