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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bc = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bc.root)

        bc.btStartBatteryService.setOnClickListener { btStartBatteryServiceListener() }
        bc.btStopBatteryService.setOnClickListener { btStopBatteryServiceListener() }
    }

    private fun btStartBatteryServiceListener() {
        ContextCompat.startForegroundService(this,BatteryService.newIntent(this))
    }

    private fun btStopBatteryServiceListener(){
        stopService(BatteryService.newIntent(this))
    }
}