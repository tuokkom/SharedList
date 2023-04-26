package com.tuokko.sharedlist.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuokko.sharedlist.BaseView
import com.tuokko.sharedlist.ItemListAdapter
import com.tuokko.sharedlist.databinding.SingleItemBinding

class SingleItemViewImp(inflater: LayoutInflater, parent: ViewGroup): BaseView<SingleItemView.Listener>(), SingleItemView {

    private var binding: SingleItemBinding = SingleItemBinding.inflate(inflater, parent, false)

    init {
        setRootView(binding.root)
    }

    override fun setItem(item: ItemListAdapter.SingleItem) {
        binding.itemText.text = item.name
        binding.itemCheckbox.isChecked = item.checked
    }
}