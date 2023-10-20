package com.tuokko.sharedlist.controllers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tuokko.sharedlist.models.AppPreferences
import com.tuokko.sharedlist.models.ListService
import com.tuokko.sharedlist.models.Navigator
import com.tuokko.sharedlist.views.listview.ItemListAdapter
import com.tuokko.sharedlist.views.listview.ListView

class MainListActivity : BaseActivity(), ListView.Listener, ListService.Listener {

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
    private lateinit var listService: ListService

    private var listId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = compositionRoot.mainListView
        prefs = compositionRoot.appPreferences
        navigator = compositionRoot.navigator
        listService = compositionRoot.listService

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
        listService.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        listService.removeListener(this)
    }

    private fun updateListFromDatabase() {
        if (listId == null) return
        listService.requestItems(listId!!)
    }

    override fun onItemClicked(item: ItemListAdapter.SingleItem) {
        Log.d(TAG, "onItemClicked() Item name: ${item.name}")
        listService.updateItemChecked(listId, item)

    }

    override fun onItemAdded(item: ItemListAdapter.SingleItem) {
        Log.d(TAG, "onItemAdded() Item name: ${item.name}")
        if (item.name.isEmpty()) {
            Log.d(TAG, "onItemAdded() Item to be added is empty.")
            return
        }
        if (listId == null) return

        listService.addItemToList(listId!!, item.name)
    }

    override fun onDeleteItemsClicked() {
        Log.d(TAG, "onDeleteItemsClicked() Called")
        if (listId == null) return
        listService.deleteCheckedItems(listId!!)
    }

    override fun onChangeListClicked() {
        navigator.navigateToChangeListActivity()
    }

    override fun onListItemDeleted(itemName: String) {
        view.deleteListItem(itemName)
    }

    override fun onListItemsGot(items: List<ItemListAdapter.SingleItem>) {
        view.updateListItems(items)
    }

    override fun onListItemUpdated(item: ItemListAdapter.SingleItem) {
        view.updateListItem(item)
    }

    override fun onFailure() {
        Log.d(TAG, "onFailure() Called")
    }
}