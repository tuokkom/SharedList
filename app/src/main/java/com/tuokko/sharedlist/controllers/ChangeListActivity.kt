package com.tuokko.sharedlist.controllers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tuokko.sharedlist.R
import com.tuokko.sharedlist.screens.MainActivity
import com.tuokko.sharedlist.views.ChangeListView

class ChangeListActivity : AppCompatActivity(), ChangeListView.Listener {
    companion object {
        const val TAG = "ChangeListActivity"
    }

    private lateinit var view: ChangeListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ChangeListView(layoutInflater, null);

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
        val sharedPref = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(getString(R.string.list_key), list)
        editor.apply()
        updateListID()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateListID() {
        val sharedPref = getSharedPreferences(getString(R.string.shared_prefs), MODE_PRIVATE)
        val listID = sharedPref.getString(getString(R.string.list_key), "") ?: return
        view.setCurrentListId(listID)
    }
}