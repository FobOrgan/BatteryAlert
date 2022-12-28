package com.fobos.batteryalert

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BatteryService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("DebugInfo","Сервис BatteryService запуcкается!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DebugInfo","Сервис BatteryService запущен!")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        Log.d("DebugInfo","Сервис BatteryService выключен!")
        super.onDestroy()
    }

}