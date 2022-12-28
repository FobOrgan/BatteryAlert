package com.fobos.batteryalert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fobos.batteryalert.data.BatteryBroadcastReceiver
import kotlinx.coroutines.*

class BatteryService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val batteryBroadcastReceiver = BatteryBroadcastReceiver()

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()

        startForeground(NOTIFICATION_ID,createNotification())
        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryBroadcastReceiver, batteryFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            while (true){
                delay(1000)
            }
            //stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String){
        Log.d("DebugInfo","BatteryService message: $message")
    }

    private fun createNotificationChannel(){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Title")
        .setContentText("Text")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .build()

    companion object{
        private const val CHANNEL_ID = "battery_service_channel_id"
        private const val CHANNEL_NAME = "battery_service_channel"
        private const val NOTIFICATION_ID = 1

        fun newIntent(context: Context): Intent{
            return Intent(context, BatteryService::class.java)
        }
    }

}