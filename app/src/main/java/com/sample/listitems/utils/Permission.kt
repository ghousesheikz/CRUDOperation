package com.sample.listitems.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission(context: Activity) {
    var activity: Activity? = null
    private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100
    private var isAccessGranted = false


    init {
        this.activity = context
        isAccessGranted = false
    }


    fun grantAccess(dialogMsg: Int) {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            !== PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(activity)
                    .setTitle("Grant Access")
                    .setMessage(dialogMsg)
                    .setPositiveButton("OK") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )
                    }
                    .setNegativeButton(
                        "Cancel"
                    ) { dialog, which -> dialog.dismiss() }
                    .setCancelable(false)
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }
        } else {
            isAccessGranted = true
        }
    }

    fun isAccessGranted(): Boolean {
        return isAccessGranted
    }

    fun setAccessGranted(accessGranted: Boolean) {
        isAccessGranted = accessGranted
    }
}