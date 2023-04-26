package com.tuokko.sharedlist

import android.view.View

interface ObservableView<ListenerType> {
    fun getRootView(): View

    fun addListener(listener: ListenerType)
    fun removeListener(listener: ListenerType)
}