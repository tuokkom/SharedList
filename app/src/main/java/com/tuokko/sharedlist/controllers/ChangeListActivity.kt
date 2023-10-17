package com.tuokko.sharedlist.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tuokko.sharedlist.models.AppPreferences
import com.tuokko.sharedlist.views.ChangeListView

class ChangeListActivity : AppCompatActivity(), ChangeListView.Listener {
    companion object {
        const val TAG = "ChangeListActivity"
    }

    private lateinit var view: ChangeListView
    private val prefs = AppPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ChangeListView(layoutInflater, null)

        setContentView(view.getRootView())

        updateListID()
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
    }

    override fun onStop() {
        view.removeListener(this)
        super.onStop()
    }

    override fun onJoinListClicked(listId: String) {
        joinList(listId)
    }

    private fun joinList(list: String) {
        Log.d(TAG, "New list to join: $list")
        prefs.setListId(list)
        updateListID()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateListID() {
        val listId = prefs.getListId() ?: return
        view.setCurrentListId(listId)
    }
}