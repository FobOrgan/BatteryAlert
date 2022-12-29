package com.fobos.batteryalert.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import com.fobos.batteryalert.data_source.BatteryData
import com.fobos.batteryalert.domain.UpdateBatteryDataListener

class BatteryBroadcastReceiver(var updateBatteryDataListener: UpdateBatteryDataListener): BroadcastReceiver() {
    lateinit var batteryData: BatteryData

    override fun onReceive(context: Context?, intent: Intent?) {
        val chargePercent = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?:0
        val voltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?:0
        val temperature = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)!!/10.0
        batteryData = BatteryData(chargePercent,voltage,temperature)
        updateBatteryDataListener.updateBatteryData(batteryData)
    }
}