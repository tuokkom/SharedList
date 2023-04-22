package com.tuokko.sharedlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tuokko.sharedlist.screens.MainActivity
//import kotlinx.android.synthetic.main.activity_change_list.*

class ChangeListActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ChangeListActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_list)

        /*
        joinListButton.setOnClickListener {
            val listToBeJoined = listEditText.text.toString()
            if (listToBeJoined.isEmpty()) {
                emptlyListErrorText.alpha = 1.0f
                return@setOnClickListener
            }
            joinList(listToBeJoined)
        }
        updateListID()

         */
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
        val listID = sharedPref.getString(getString(R.string.list_key), "")
        //currentListId.text = listID
    }
}