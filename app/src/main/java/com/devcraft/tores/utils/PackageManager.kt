package com.devcraft.tores.utils

import android.content.pm.PackageManager.GET_ACTIVITIES
import com.devcraft.tores.app.App

object PackageManager {
    fun isAppAvailable(packageName: String): Boolean {
        val pm: android.content.pm.PackageManager = App.instance.packageManager
        return try {
            pm.getPackageInfo(packageName, GET_ACTIVITIES)
            true
        } catch (e: android.content.pm.PackageManager.NameNotFoundException) {
            false
        }
    }
}