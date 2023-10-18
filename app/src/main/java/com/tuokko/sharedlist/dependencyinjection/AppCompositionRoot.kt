package com.tuokko.sharedlist.dependencyinjection

import android.content.Context
import com.tuokko.sharedlist.models.AppPreferences

class AppCompositionRoot(private val context: Context) {

    val appPreferences get() = AppPreferences(context)
}