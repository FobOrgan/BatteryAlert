package com.fobos.batteryalert.domain

import com.fobos.batteryalert.data_source.BatteryData

interface UpdateBatteryDataListener {
    fun updateBatteryData(newBatteryData:BatteryData)
}