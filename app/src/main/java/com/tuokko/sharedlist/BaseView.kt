package com.tuokko.sharedlist

import android.content.Context
import android.view.View

abstract class BaseView: ObservableView {

    private lateinit var rootView: View

    protected fun setRootView(view: View) {
        rootView = view
    }

    override fun getRootView(): View {
        return rootView
    }

    protected fun getContext(): Context {
        return rootView.context
    }
}