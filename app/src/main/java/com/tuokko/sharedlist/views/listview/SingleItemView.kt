package com.tuokko.sharedlist.views.listview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuokko.sharedlist.views.common.BaseView
import com.tuokko.sharedlist.databinding.SingleItemBinding

class SingleItemView(inflater: LayoutInflater, parent: ViewGroup): BaseView<SingleItemView.Listener>() {

    interface Listener {
        fun onItemClicked(item: ItemListAdapter.SingleItem)
    }

    private var binding: SingleItemBinding = SingleItemBinding.inflate(inflater, parent, false)

    init {
        setRootView(binding.root)

        binding.itemParentLayout.setOnClickListener {
            val name = binding.itemText.text.toString()
            val checked = binding.itemCheckbox.isChecked
            for (listener in getListeners()) {
                listener.onItemClicked(ItemListAdapter.SingleItem(name, !checked))
            }
        }
    }

    fun setItem(item: ItemListAdapter.SingleItem) {
        binding.itemText.text = item.name
        binding.itemCheckbox.isChecked = item.checked
    }
}