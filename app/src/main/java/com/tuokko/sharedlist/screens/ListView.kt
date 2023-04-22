package com.tuokko.sharedlist.screens

import android.content.Context
import android.view.View
import com.tuokko.sharedlist.BaseView
import com.tuokko.sharedlist.ItemListAdapter
import com.tuokko.sharedlist.ObservableView

interface ListView: ObservableView {
    interface Listener {
        fun onItemClicked(item: ItemListAdapter.SingleItem)
        fun onItemAdded(item: ItemListAdapter.SingleItem)
        fun onDeleteItemsClicked()
    }
    fun addListener(listener: Listener)
    fun removeListener(listener: Listener)

    fun updateListItems(items: List<ItemListAdapter.SingleItem>)

}