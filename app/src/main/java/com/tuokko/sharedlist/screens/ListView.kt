package com.tuokko.sharedlist.screens

import com.tuokko.sharedlist.ItemListAdapter
import com.tuokko.sharedlist.ObservableView

interface ListView: ObservableView<ListView.Listener> {
    interface Listener {
        fun onItemClicked(item: ItemListAdapter.SingleItem)
        fun onItemAdded(item: ItemListAdapter.SingleItem)
        fun onDeleteItemsClicked()
    }

    fun updateListItems(items: List<ItemListAdapter.SingleItem>)

}