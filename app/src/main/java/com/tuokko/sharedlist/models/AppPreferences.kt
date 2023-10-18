package com.tuokko.sharedlist.models

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.tuokko.sharedlist.R

class AppPreferences(private val context: Context) {

    fun setListId(listId: String) {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.shared_prefs),
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = sharedPref.edit()
        editor.putString(context.getString(R.string.list_key), listId)
        editor.apply()
    }

    fun getListId(): String? {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.shared_prefs),
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPref.getString(context.getString(R.string.list_key), "")
    }
}