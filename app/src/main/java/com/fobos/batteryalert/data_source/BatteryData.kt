package com.fobos.batteryalert.data_source

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

data class BatteryData(
    var chargePercent: Int,
    var voltage:Int,
    var temperature: Double
    )