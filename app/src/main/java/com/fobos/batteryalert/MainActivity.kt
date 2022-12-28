package com.fobos.batteryalert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.fobos.batteryalert.data_source.BatteryData
import com.fobos.batteryalert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bc: ActivityMainBinding
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bc.root)

        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(broadcastReceiver, batteryFilter)
        bc.btTest.setOnClickListener { btTestSetOnClickListener() }

        //startForegroundService(Intent(this,BatteryService::class.java))
        //startService(Intent(this,BatteryService::class.java))
    }


    private fun btTestSetOnClickListener() {
        bc.tvBatLevel.text = getBatteryPercentage(this).toString()
        //stopService(Intent(this,BatteryService::class.java))
    }

    private fun getBatteryPercentage(context: Context): Int {
        val bm = context.getSystemService(BATTERY_SERVICE) as BatteryManager
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

}