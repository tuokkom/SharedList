package com.tuokko.sharedlist.screens

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuokko.sharedlist.BaseView
import com.tuokko.sharedlist.ItemListAdapter
import com.tuokko.sharedlist.R
import com.tuokko.sharedlist.databinding.ActivityMainBinding

class ListViewImp(inflater: LayoutInflater, parent: ViewGroup?): BaseView(),
    ListView, ItemListAdapter.Listener {
    companion object {
        private const val TAG = "ListViewImp"
    }

    private var binding: ActivityMainBinding = ActivityMainBinding.inflate(inflater)
    private val listeners = mutableListOf<ListView.Listener>()
    private var itemListAdapter: ItemListAdapter = ItemListAdapter(this)

    override fun addListener(listener: ListView.Listener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: ListView.Listener) {
        listeners.remove(listener)
    }


    init {
        binding.addItemButton.setOnClickListener {
            Log.d(TAG, "init() addItemButton clicked")
            val itemToBeAdded = ItemListAdapter.SingleItem(binding.addItemEditText.text.toString(), false)
            for (listener in listeners) {
                listener.onItemAdded(itemToBeAdded)
            }
        }

        binding.deleteItemsLayout.setOnClickListener {
            Log.d(TAG, "init() deleteItemsLayout clicked")
            for (listener in listeners) {
                listener.onDeleteItemsClicked()
            }
        }

        val itemListRecyclerView = binding.itemListRecyclerView
        itemListRecyclerView.adapter = itemListAdapter
        itemListRecyclerView.layoutManager = LinearLayoutManager(getContext())

        setRootView(binding.root)
    }


    override fun updateListItems(items: List<ItemListAdapter.SingleItem>) {
        for (item in items) {
            Log.d(TAG, "updateItems() Item in list: ${item.name} checked: ${item.checked}")
            itemListAdapter.addItemToList(item)
        }
    }

    override fun onItemClicked(item: ItemListAdapter.SingleItem) {
        for (listener in listeners) {
            listener.onItemClicked(item)
        }
    }
}