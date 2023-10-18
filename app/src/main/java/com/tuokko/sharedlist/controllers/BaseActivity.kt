package com.tuokko.sharedlist.controllers

import androidx.appcompat.app.AppCompatActivity
import com.tuokko.sharedlist.dependencyinjection.ActivityCompositionRoot

open class BaseActivity: AppCompatActivity() {
    protected val compositionRoot get() = ActivityCompositionRoot(this)
}