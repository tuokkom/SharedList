package com.tuokko.sharedlist.views.listview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuokko.sharedlist.databinding.ActivityMainBinding
import com.tuokko.sharedlist.databinding.ToolbarBinding
import com.tuokko.sharedlist.views.common.BaseView

class ListViewImp(inflater: LayoutInflater, parent: ViewGroup?): BaseView<ListView.Listener>(),
    ListView, ItemListAdapter.Listener {
    companion object {
        private const val TAG = "ListViewImp"
    }

    private var binding: ActivityMainBinding = ActivityMainBinding.inflate(inflater, parent, false)
    private var itemListAdapter: ItemListAdapter = ItemListAdapter(this)

    private var toolBarBinding: ToolbarBinding = ToolbarBinding.inflate(inflater, binding.toolbar, true)

    init {
        setRootView(binding.root)

        binding.addItemButton.setOnClickListener {
            Log.d(TAG, "init() addItemButton clicked")
            val itemToBeAdded =
                ItemListAdapter.SingleItem(binding.addItemEditText.text.toString(), false)
            for (listener in getListeners()) {
                listener.onItemAdded(itemToBeAdded)
            }
        }

        binding.deleteItemsLayout.setOnClickListener {
            Log.d(TAG, "init() deleteItemsLayout clicked")
            for (listener in getListeners()) {
                listener.onDeleteItemsClicked()
            }
        }

        binding.itemListRecyclerView.apply {
            adapter = itemListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        toolBarBinding.changeList.setOnClickListener {
            for (listener in getListeners()) {
                listener.onChangeListClicked()
            }
        }
    }


    override fun updateListItems(items: List<ItemListAdapter.SingleItem>) {
        for (item in items) {
            Log.d(TAG, "updateItems() Item in list: ${item.name} checked: ${item.checked}")
            itemListAdapter.addItemToList(item)
        }
        binding.addItemEditText.text.clear()
    }

    override fun updateListItem(item: ItemListAdapter.SingleItem) {
        itemListAdapter.updateItem(item)
    }

    override fun deleteListItem(itemName: String) {
        itemListAdapter.deleteItemFromList(itemName)
    }

    override fun onItemClicked(item: ItemListAdapter.SingleItem) {
        for (listener in getListeners()) {
            listener.onItemClicked(item)
        }
    }
}