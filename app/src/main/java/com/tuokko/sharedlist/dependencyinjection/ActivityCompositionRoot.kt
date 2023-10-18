package com.tuokko.sharedlist.dependencyinjection

import androidx.appcompat.app.AppCompatActivity
import com.tuokko.sharedlist.ListApplication
import com.tuokko.sharedlist.models.Navigator
import com.tuokko.sharedlist.views.ChangeListView

class ActivityCompositionRoot(private val activity: AppCompatActivity) {
    private val appCompositionRoot get() = (activity.application as ListApplication).appCompositionRoot

    val navigator get() = Navigator(activity)
    val appPreferences get() = appCompositionRoot.appPreferences

    val changeListView get() = ChangeListView(activity.layoutInflater, null)
}