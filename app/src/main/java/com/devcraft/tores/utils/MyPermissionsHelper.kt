package com.devcraft.tores.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class MyPermissionsHelper {
    companion object {
        private fun shouldAskPermission(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        }

        fun onlyCheckIfAllPermissionsGranted(
            ctx: Context,
            permissions: Array<String>
        ): Boolean {
            if (!shouldAskPermission()) return true
            var permissionsAlreadyGranted = true
            permissions.forEach {
                val result = ContextCompat.checkSelfPermission(ctx, it)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionsAlreadyGranted = false
                }
            }
            return permissionsAlreadyGranted
        }

        fun checkAndRequestPermissions(
            act: AppCompatActivity,
            requestCode: Int,
            permissions: Array<String>,
            callback: Callback? = null
        ) {
            var permissionsAlreadyGranted = true
            if (shouldAskPermission()) {
                permissions.forEach {
                    val result = ContextCompat.checkSelfPermission(act, it)
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionsAlreadyGranted = false
                    }
                }
            }
            if (permissionsAlreadyGranted) {
                callback?.onPermissionsGranted(requestCode, permissions)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    var somePermissionPermanentlyDenied = false
                    permissions.forEach {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(act, it)) {
                            if (PreferencesHelper.isFirstTimeAskingPermission(it)) {
                                PreferencesHelper.setPermissionAsked(it)
                            } else {
                                somePermissionPermanentlyDenied = true
                            }
                        }
                    }

                    if (somePermissionPermanentlyDenied) {
                        callback?.onPermissionsPermanentlyDenied(requestCode, permissions)
                    } else {
                        act.requestPermissions(permissions, requestCode)
                    }
                }
            }
        }

        fun checkAndRequestPermissions(
            frag: Fragment,
            requestCode: Int,
            permissions: Array<String>,
            callback: Callback? = null
        ) {
            var permissionsAlreadyGranted = true
            if (shouldAskPermission()) {
                permissions.forEach {
                    val result = ContextCompat.checkSelfPermission(frag.requireActivity(), it)
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permissionsAlreadyGranted = false
                    }
                }
            }
            if (permissionsAlreadyGranted) {
                callback?.onPermissionsGranted(requestCode, permissions)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    var somePermissionPermanentlyDenied = false
                    permissions.forEach {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                frag.requireActivity(),
                                it
                            )
                        ) {
                            if (PreferencesHelper.isFirstTimeAskingPermission(it)) {
                                PreferencesHelper.setPermissionAsked(it)
                            } else {
                                somePermissionPermanentlyDenied = true
                            }
                        }
                    }

                    if (somePermissionPermanentlyDenied) {
                        callback?.onPermissionsPermanentlyDenied(requestCode, permissions)
                    } else {
                        frag.requestPermissions(permissions, requestCode)
                    }
                }
            }
        }

        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray,
            callback: Callback? = null
        ) {
            if (grantResults.isEmpty()) {
                //canceled
                callback?.onPermissionsDenied(requestCode, permissions)
            } else {
                var isAllGranted = true
                grantResults.forEach {
                    if (it != PackageManager.PERMISSION_GRANTED) {
                        isAllGranted = false
                    }
                }
                if (isAllGranted) {
                    callback?.onPermissionsGranted(requestCode, permissions)
                } else {
                    callback?.onPermissionsDenied(requestCode, permissions)
                }
            }
        }

    }

    object PreferencesHelper {
        fun isFirstTimeAskingPermission(permission: String): Boolean {
            return if (!PreferencesUtils.isExists(permission)) true else !PreferencesUtils.getBoolean(
                permission
            )
        }

        fun setPermissionAsked(permission: String) {
            PreferencesUtils.setBoolean(permission, true)
        }
    }

    interface Callback {
        fun onPermissionsGranted(requestCode: Int, perms: Array<String>)
        fun onPermissionsDenied(requestCode: Int, perms: Array<String>)
        fun onPermissionsPermanentlyDenied(requestCode: Int, perms: Array<String>)
    }
}