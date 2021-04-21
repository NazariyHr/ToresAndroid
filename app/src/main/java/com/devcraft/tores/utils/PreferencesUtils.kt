package com.devcraft.tores.utils

import android.content.Context
import com.devcraft.tores.app.App
import com.devcraft.tores.app.Constants

object PreferencesUtils {
    private val sp =
        App.instance.getSharedPreferences(Constants.COMMON_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun putString(name: String, value: String) {
        sp.edit().putString(name, value).apply()
    }

    fun getString(name: String): String {
        return sp.getString(name, "") ?: ""
    }

    fun isExists(name: String): Boolean {
        return sp.contains(name)
    }

    fun getBoolean(name: String): Boolean {
        return sp.getBoolean(name, false)
    }

    fun setBoolean(name: String, value: Boolean) {
        sp.edit().putBoolean(name, value).apply()
    }
}