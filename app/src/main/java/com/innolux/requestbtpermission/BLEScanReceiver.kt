package com.innolux.requestbtpermission

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BLEScanReceiver : BroadcastReceiver() {

    private val TAG = this.javaClass.simpleName

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d(TAG, "The received action is ${intent.action}")
    }
}
