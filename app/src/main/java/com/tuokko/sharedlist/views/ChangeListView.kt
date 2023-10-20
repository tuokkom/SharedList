package com.tuokko.sharedlist.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tuokko.sharedlist.databinding.ActivityChangeListBinding
import com.tuokko.sharedlist.databinding.ToolbarBinding
import com.tuokko.sharedlist.views.common.BaseView

class ChangeListView(inflater: LayoutInflater, parent: ViewGroup?) : BaseView<ChangeListView.Listener>() {

    interface Listener{
        fun onJoinListClicked(listId: String)
    }

    private var binding: ActivityChangeListBinding = ActivityChangeListBinding.inflate(inflater, parent, false)

    private var toolBarBinding: ToolbarBinding = ToolbarBinding.inflate(inflater, binding.toolbar.root, true)

    init {
        setRootView(binding.root)
        binding.joinListButton.setOnClickListener {
            for (listener in getListeners()) {
                listener.onJoinListClicked(binding.listEditText.text.toString())
            }
        }
        toolBarBinding.changeList.visibility = View.GONE
    }

    fun setCurrentListId(listId: String){
        binding.currentListId.text = listId
    }
}