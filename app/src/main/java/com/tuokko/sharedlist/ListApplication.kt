package com.tuokko.sharedlist

import android.app.Application
import com.tuokko.sharedlist.dependencyinjection.AppCompositionRoot

class ListApplication: Application() {
    val appCompositionRoot = AppCompositionRoot(this)
}