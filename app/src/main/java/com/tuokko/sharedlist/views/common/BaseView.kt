package com.tuokko.sharedlist.views.common

import android.content.Context
import android.view.View

abstract class BaseView<ListenerType>: ObservableView<ListenerType> {

    private lateinit var rootView: View
    private var listeners: MutableList<ListenerType> = mutableListOf()
    protected fun setRootView(view: View) {
        rootView = view
    }

    override fun getRootView(): View {
        return rootView
    }

    protected fun getContext(): Context {
        return rootView.context
    }

    override fun addListener(listener: ListenerType) {
        listeners.add(listener)
    }

    override fun removeListener(listener: ListenerType) {
        listeners.remove(listener)
    }

    fun getListeners(): List<ListenerType> {
        return listeners
    }
}