package com.tuokko.sharedlist.controllers

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuokko.sharedlist.R
import com.tuokko.sharedlist.views.listview.ItemListAdapter
import com.tuokko.sharedlist.views.listview.ListView
import com.tuokko.sharedlist.views.listview.ListViewImp

class MainListActivity : AppCompatActivity(), ListView.Listener {

    companion object {
        private const val TAG = "MainActivity"
        fun start(context: Context) {
            val intent = Intent(context, MainListActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var listView: ListView
    private var listID: String? = null
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listView = ListViewImp(layoutInflater, null)
        listView.addListener(this)

        getCurrentListID()

        updateListFromDatabase()

        setContentView(listView.getRootView())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_view_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.changeListMenuItem -> {
                val intent = Intent(this, ChangeListActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addItemToList(item: String) {
        Log.d(TAG, "Adding item to recyclerView $item")
        if (listID == null) return
        val listItemProperties = hashMapOf(
            "checked" to false
        )
        db.collection(listID!!).document(item).set(listItemProperties)
            .addOnSuccessListener {
                updateListFromDatabase()
            }
            .addOnFailureListener {
                Log.d(TAG, "Adding the item: $item to list: $listID failed, error ${it.message}")
            }
    }

    private fun updateListFromDatabase() {
        if (listID == null) return


        db.collection(listID!!).get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemListAdapter.SingleItem>()
                for (item in result) {
                    Log.d(TAG, "Received data from database, item: ${item.id}")
                    Log.d(TAG, "Received data from database, more info: ${item.data}")
                    val checkState: Boolean = item.data["checked"] as Boolean
                    itemList.add(ItemListAdapter.SingleItem(item.id, checkState))
                }
                listView.updateListItems(itemList)
        }


    }

    private fun getCurrentListID() {
        val sharedPref = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE)
        listID = sharedPref.getString(getString(R.string.list_key), "")
        if (listID.isNullOrEmpty()) {
            val intent = Intent(this, ChangeListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun deleteSelectedItems() {
        if (listID == null) return
        db.collection(listID!!).get()
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
        if (listID == null) return
        db.collection(listID!!).document(itemName)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "deleteSingleItem() Item: $itemName deleted")
                    //itemListAdapter.deleteItemFromList(itemName)
                    listView.deleteListItem(itemName)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to delete item: $itemName, error: ${it.message}")
                }
    }

    override fun onItemClicked(item: ItemListAdapter.SingleItem) {
        Log.d(TAG, "onItemClicked() Item name: ${item.name}")
        db.collection(listID!!).document(item.name).update("checked", item.checked)
            .addOnSuccessListener {
                Log.d(TAG, "onItemClicked() Item updated to state: ${item.checked}")
                listView.updateListItem(item)
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
}