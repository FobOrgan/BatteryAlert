package com.fobos.batteryalert

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.fobos.batteryalert.data.BatteryBroadcastReceiver
import com.fobos.batteryalert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bc: ActivityMainBinding
   // val batteryBroadcastReceiver = BatteryBroadcastReceiver()
    /*private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val batVoltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
            val batLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            var batTemperature = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)!!/10.0

            Log.d("DebugInfo", "" +
                    "Battery info: level = ${batLevel}% " +
                    "voltage = ${batVoltage}V " +
                    "temperature = ${batTemperature}°С")
        }
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bc.root)

       // val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
       // registerReceiver(batteryBroadcastReceiver, batteryFilter)
        bc.btStartBatteryService.setOnClickListener { btStartBatteryServiceListener() }
        bc.btStopBatteryService.setOnClickListener { btStopBatteryServiceListener() }

        //startForegroundService(Intent(this,BatteryService::class.java))
        //startService(Intent(this,BatteryService::class.java))
    }

    private fun btStartBatteryServiceListener() {
        ContextCompat.startForegroundService(this,BatteryService.newIntent(this))
    }

    private fun btStopBatteryServiceListener(){
        stopService(BatteryService.newIntent(this))
    }
}