package com.tu.pmu.the100th.data.provider.interfaces

import android.location.Location

interface LocationProvider {

    fun isLocationEnabled(): Boolean

    //suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    //suspend fun getPreferredLocationString(): String
    suspend fun getLastPhysicalDeviceLocation(): Location?

    companion object {
        const val TAG = "LocationProvider"
    }

    suspend fun hasLocationChanged(lastLocation: Location): Boolean
}