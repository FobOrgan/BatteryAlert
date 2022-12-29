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
import com.fobos.batteryalert.data_source.BatteryData
import com.fobos.batteryalert.domain.UpdateBatteryDataListener
import kotlinx.coroutines.*

class BatteryService : Service(),UpdateBatteryDataListener {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val batteryBroadcastReceiver = BatteryBroadcastReceiver(this)
    private val notificationManager by lazy {  getSystemService(NOTIFICATION_SERVICE) as NotificationManager}
    private val notificationBuilder = createNotification()
    private var currentBatteryData = BatteryData(0,0,0.0)
    private var newBatteryData = BatteryData(0,0,0.0)


    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createNotificationChannel()

        startForeground(NOTIFICATION_ID,notificationBuilder.build())

        val batteryFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryBroadcastReceiver, batteryFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            while (true){
                delay(1000)
                if (currentBatteryData != newBatteryData){
                    updateNotification()
                    currentBatteryData = newBatteryData
                }
            }
        }
        return START_STICKY
    }

    private fun updateNotification(){
        notificationBuilder.setContentText("" +
                "${batteryBroadcastReceiver.batteryData.voltage}"+
                " ${batteryBroadcastReceiver.batteryData.temperature}"+
                " ${batteryBroadcastReceiver.batteryData.chargePercent}")
        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build())
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Title")
        .setContentText("Text")
        .setSmallIcon(R.drawable.ic_launcher_background)

    override fun updateBatteryData(newBatteryData: BatteryData) {
        this.newBatteryData = newBatteryData
    }

    companion object{

        private const val CHANNEL_ID = "battery_service_channel_id"
        private const val CHANNEL_NAME = "battery_service_channel"
        private const val NOTIFICATION_ID = 1

        fun newIntent(context: Context): Intent{
            return Intent(context, BatteryService::class.java)
        }
    }
}