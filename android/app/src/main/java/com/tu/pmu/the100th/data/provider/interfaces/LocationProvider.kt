package com.tu.pmu.the100th.data.provider.interfaces

import android.location.Location
import com.google.android.gms.maps.model.LatLng

interface LocationProvider {
    val TAG: String
        get() = "LocationProvider"

    fun isLocationEnabled(): Boolean

    //suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    //suspend fun getPreferredLocationString(): String
    suspend fun getLastPhysicalDeviceLocation(): Location?

    companion object {
        const val TAG = "LocationProvider"
    }

    suspend fun hasLocationChanged(lastLocation: Location): Boolean
}