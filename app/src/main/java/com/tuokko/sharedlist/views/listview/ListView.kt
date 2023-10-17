package com.tuokko.sharedlist.views.listview

import com.tuokko.sharedlist.views.common.ObservableView

interface ListView: ObservableView<ListView.Listener> {
    interface Listener {
        fun onItemClicked(item: ItemListAdapter.SingleItem)
        fun onItemAdded(item: ItemListAdapter.SingleItem)
        fun onDeleteItemsClicked()
    }

    fun updateListItems(items: List<ItemListAdapter.SingleItem>)
    fun updateListItem(item: ItemListAdapter.SingleItem)
    fun deleteListItem(itemName: String)
}