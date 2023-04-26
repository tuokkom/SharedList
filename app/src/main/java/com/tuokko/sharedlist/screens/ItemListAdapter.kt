package com.tuokko.sharedlist.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemListAdapter(private val listener: Listener) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>(), SingleItemView.Listener
{
    companion object {
        private const val TAG = "ItemListAdapter"
    }

    private val itemArray: ArrayList<SingleItem> = ArrayList()

    interface Listener {
        fun onItemClicked(item: SingleItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val singleItem = SingleItemViewImp(LayoutInflater.from(parent.context), parent)
        singleItem.addListener(this)
        return ViewHolder(singleItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() Binding single view at position: $position")
        holder.singleItemView.setItem(itemArray[position])
    }

    override fun getItemCount(): Int {
        return itemArray.size
    }

    override fun onItemClicked(item: SingleItem) {
        listener.onItemClicked(item)
    }

    fun addItemToList(item: SingleItem) {
        val alreadyAdded = itemArray.any {
            it.name == item.name
        }
        if (alreadyAdded) return
        itemArray.add(item)
        notifyItemChanged(itemArray.lastIndex)
    }

    fun updateItem(item: SingleItem) {
        val itemToBeUpdated = itemArray.find { it.name == item.name }
        if (itemToBeUpdated == null || itemToBeUpdated.checked == item.checked) return
        val indexToBeUpdated = itemArray.indexOf(itemToBeUpdated)
        itemArray[indexToBeUpdated] = item
        notifyItemChanged(indexToBeUpdated)
    }
    fun deleteItemFromList(item: String) {
        for (it in itemArray) {
            if (it.name == item) {
                itemArray.remove(it)
                break
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolder(val singleItemView: SingleItemView) : RecyclerView.ViewHolder(singleItemView.getRootView())

    class SingleItem(val name: String, val checked: Boolean)
}