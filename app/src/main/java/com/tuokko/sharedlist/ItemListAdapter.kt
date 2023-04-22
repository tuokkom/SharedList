package com.tuokko.sharedlist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemListAdapter(val listener: Listener) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>()
{
    private val itemArray: ArrayList<SingleItem> = ArrayList()

    interface Listener {
        fun onItemClicked(item: SingleItem)
    }

    companion object {
        private const val TAG = "ItemListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "Binding sigle view at position: $position")
        holder.setItemText(itemArray[position].name)
        holder.setItemChecked(itemArray[position].checked)
    }

    override fun getItemCount(): Int {
        return itemArray.size
    }

    fun addItemToList(item: SingleItem) {
        val alreadyAdded = itemArray.any {
            it.name == item.name
        }
        if (alreadyAdded) return
        itemArray.add(item)
        notifyDataSetChanged()
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*
        init {
            itemView.findViewById<ConstraintLayout>(R.id.itemParentLayout).setOnClickListener {
                if (listID == null) return@setOnClickListener
                Log.d(TAG, "Single item clicked")
                val checkBox = itemView.findViewById<CheckBox>(R.id.itemCheckbox)
                val newValueForChecked = !checkBox.isChecked
                val itemName = itemView.findViewById<TextView>(R.id.itemText).text.toString()
                Firebase.firestore.collection(listID).document(itemName).update("checked", newValueForChecked)
                        .addOnSuccessListener {
                            checkBox.isChecked = newValueForChecked
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Updating checked value failed, error: ${it.message}")
                        }
            }
        }

         */
        fun setItemText(text: String) {
            Log.d(TAG, "Adding single list item text to: $text")
            itemView.findViewById<TextView>(R.id.itemText).text = text
        }
        fun setItemChecked(checked: Boolean) {
            itemView.findViewById<CheckBox>(R.id.itemCheckbox).isChecked = checked
        }
    }

    class SingleItem(val name: String, val checked: Boolean)
}