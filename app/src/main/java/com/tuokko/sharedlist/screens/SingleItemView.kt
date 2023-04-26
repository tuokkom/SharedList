package com.tuokko.sharedlist.screens

import com.tuokko.sharedlist.ItemListAdapter
import com.tuokko.sharedlist.ObservableView

interface SingleItemView: ObservableView<SingleItemView.Listener> {
    interface Listener {
        fun onItemClicked(item: ItemListAdapter.SingleItem)
    }
    fun setItem(item: ItemListAdapter.SingleItem)
}