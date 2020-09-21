package com.tu.pmu.the100th.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.internal.utils.LocationPermissionNotGrantedException
import com.tu.pmu.the100th.internal.utils.asDeferred
import java.lang.Math.abs

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    val context: Context
) : LocationProvider {

//
//    override fun getLocationString(): String {
//        return getCustomLocationName()!!
//    }

    override suspend fun hasLocationChanged(lastLocation: Location): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }
        return deviceLocationChanged //|| hasCustomLocationChanged(lastWeatherLocation)
    }

    // default is false for WeatherStack api and true for OpenWeather api
//    override suspend fun getPreferredLocationString(): String {
//        var customLocationName: String = "${getCustomLocationName()}"
//        if (isUsingDeviceLocation()) {
//            try {
//                val deviceLocation = getLastPhysicalDeviceLocation()
//                if (deviceLocation == null) {
//                    Log.d(LocationProvider.TAG, "getPreferredLocationString 1)new location is: $customLocationName")
//                    return customLocationName
//                }
//                Log.d(
//                    LocationProvider.TAG,
//                    ">>>getPreferredLocationString 2)new location is: ${deviceLocation.latitude},${deviceLocation.longitude} "
//                )
//                return "${deviceLocation.latitude},${deviceLocation.longitude}"
//            } catch (e: LocationPermissionNotGrantedException) {
//                Log.d(
//                    LocationProvider.TAG,
//                    "getPreferredLocationString 3)new location is: $customLocationName"
//                )
//                return customLocationName
//            }
//        } else
//            Log.d(LocationProvider.TAG, "getPreferredLocationString 4)new location is: $customLocationName")
//        return customLocationName
//    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: Location): Boolean {

        val deviceLocation = getLastPhysicalDeviceLocation()
            ?: return false

        val comparisonThreshold = 0.03
        return abs(deviceLocation.latitude - lastWeatherLocation.latitude) > comparisonThreshold &&
                abs(deviceLocation.longitude - lastWeatherLocation.longitude) > comparisonThreshold
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLastPhysicalDeviceLocation(): Location? {
        return (if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()).await()
    }

    override fun isLocationEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is new method provided in API 28
            val lm =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            lm.isLocationEnabled
        } else {
            // This is Deprecated in API 28
            val mode: Int = Settings.Secure.getInt(
                context.contentResolver, Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            )
            mode != Settings.Secure.LOCATION_MODE_OFF
        }
    }


    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}