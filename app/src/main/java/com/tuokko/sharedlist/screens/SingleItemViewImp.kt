package com.tuokko.sharedlist.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuokko.sharedlist.BaseView
import com.tuokko.sharedlist.databinding.SingleItemBinding

class SingleItemViewImp(inflater: LayoutInflater, parent: ViewGroup): BaseView<SingleItemView.Listener>(), SingleItemView {

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

    override fun setItem(item: ItemListAdapter.SingleItem) {
        binding.itemText.text = item.name
        binding.itemCheckbox.isChecked = item.checked
    }
}