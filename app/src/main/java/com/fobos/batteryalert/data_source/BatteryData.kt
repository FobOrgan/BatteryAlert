package com.fobos.batteryalert.data_source

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class BatteryData(val localBroadcastManager: LocalBroadcastManager) {
    private val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
    val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val batVoltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
            val batLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            var batTemperature = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)!!/10.0

            Log.d("DebugInfo", "" +
                    "Battery info: level = ${batLevel}% " +
                    "voltage = ${batVoltage}V " +
                    "temperature = ${batTemperature}°С")
        }
    }

    fun init():BroadcastReceiver{
        localBroadcastManager.registerReceiver(broadcastReceiver, batteryFilter)
        return broadcastReceiver
    }
}