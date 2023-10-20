package com.tuokko.sharedlist.models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuokko.sharedlist.views.listview.ItemListAdapter

class ListService {
    private val db = Firebase.firestore

    companion object {
        private const val TAG = "ListService"
    }

    interface Listener {
        fun onListItemDeleted(itemName: String)
        fun onListItemsGot(items: List<ItemListAdapter.SingleItem>)
        fun onListItemUpdated(item: ItemListAdapter.SingleItem)
        fun onFailure()
    }

    private var listeners: MutableList<Listener> = mutableListOf()

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
         listeners.remove(listener)
    }

    fun requestItems(listId: String) {
        db.collection(listId).get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ItemListAdapter.SingleItem>()
                for (item in result) {
                    Log.d(TAG, "Received data from database, item: ${item.id}")
                    Log.d(TAG, "Received data from database, more info: ${item.data}")
                    val checkState: Boolean = item.data["checked"] as Boolean
                    itemList.add(ItemListAdapter.SingleItem(item.id, checkState))
                }
                for (listener in listeners) {
                    listener.onListItemsGot(itemList)
                }
            }
            .addOnFailureListener {
                notifyFailure()
            }
    }

    fun deleteCheckedItems(listId: String) {
        db.collection(listId).get()
            .addOnSuccessListener { result ->
                for (item in result) {
                    val checkState: Boolean = item.data["checked"] as Boolean
                    if (checkState) {
                        Log.d(TAG, "Deleting item: ${item.id}")
                        deleteSingleItem(listId, item.id)
                    }
                }
            }
            .addOnFailureListener {
                notifyFailure()
            }
    }

    fun addItemToList(listId: String, item: String) {
        val listItemProperties = hashMapOf(
            "checked" to false
        )
        db.collection(listId).document(item).set(listItemProperties)
            .addOnSuccessListener {
                requestItems(listId)
            }
            .addOnFailureListener {
                Log.d(TAG, "Adding the item: $item to list: $listId failed, error ${it.message}")
                notifyFailure()
            }
    }

    fun updateItemChecked(listId: String?, item: ItemListAdapter.SingleItem) {
        db.collection(listId!!).document(item.name).update("checked", item.checked)
            .addOnSuccessListener {
                Log.d(TAG, "onItemClicked() Item updated to state: ${item.checked}")
                for (listener in listeners) {
                    listener.onListItemUpdated(item)
                }

            }
            .addOnFailureListener {
                Log.e(TAG, "onItemClicked() Item update failed")
                notifyFailure()
            }
    }

    private fun deleteSingleItem(listId: String, itemName: String) {
        db.collection(listId).document(itemName)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "deleteSingleItem() Item: $itemName deleted")
                for (listener in listeners) {
                    listener.onListItemDeleted(itemName)
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to delete item: $itemName, error: ${it.message}")
                notifyFailure()
            }
    }

    private fun notifyFailure() {
        for (listener in listeners) {
            listener.onFailure()
        }
    }
}