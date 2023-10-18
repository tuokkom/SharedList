package com.tuokko.sharedlist.controllers


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tuokko.sharedlist.models.AppPreferences
import com.tuokko.sharedlist.models.Navigator
import com.tuokko.sharedlist.views.ChangeListView

class ChangeListActivity : BaseActivity(), ChangeListView.Listener {
    companion object {
        const val TAG = "ChangeListActivity"
        fun start(context: Context) {
            val intent = Intent(context, ChangeListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    private lateinit var view: ChangeListView
    private lateinit var prefs: AppPreferences
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = compositionRoot.changeListView
        prefs = compositionRoot.appPreferences
        navigator = compositionRoot.navigator

        updateListID()

        setContentView(view.getRootView())
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
    }

    override fun onJoinListClicked(listId: String) {
        Log.d(TAG, "onJoinListClicked() New list to join: $listId")
        prefs.setListId(listId)
        updateListID()
        navigator.navigateToListActivity()
    }


    private fun updateListID() {
        val listId = prefs.getListId() ?: return
        view.setCurrentListId(listId)
    }
}