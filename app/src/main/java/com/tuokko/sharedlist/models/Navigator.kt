package com.tuokko.sharedlist.models

import android.content.Context
import com.tuokko.sharedlist.controllers.MainListActivity

class Navigator(private val context: Context) {

    fun navigateToListActivity() {
        MainListActivity.start(context)
    }
}