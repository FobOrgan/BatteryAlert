package com.fobos.batteryalert.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log

class BatteryBroadcastReceiver: BroadcastReceiver() {
    private var batLevel = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        batLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?:0
        Log.d("DebugInfo", "Battery info: level = ${batLevel}% ")
    }
}