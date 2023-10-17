package com.tuokko.sharedlist.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tuokko.sharedlist.databinding.ActivityChangeListBinding
import com.tuokko.sharedlist.views.common.BaseView

class ChangeListView(inflater: LayoutInflater, parent: ViewGroup?) : BaseView<ChangeListView.Listener>() {

    interface Listener{
        fun onJoinListClicked(listId: String)
    }

    private var binding: ActivityChangeListBinding = ActivityChangeListBinding.inflate(inflater, parent, false)

    init {
        setRootView(binding.root)
        binding.joinListButton.setOnClickListener {
            for (listener in getListeners()) {
                listener.onJoinListClicked(binding.listEditText.text.toString())
            }
        }
    }

    fun setCurrentListId(listId: String){
        binding.currentListId.text = listId
    }
}