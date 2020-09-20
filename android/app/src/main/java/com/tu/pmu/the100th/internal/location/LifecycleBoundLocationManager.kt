package com.tu.pmu.the100th.internal.location

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

private const val fastestIntervalTime: Long = 1000
private const val TAG = "LifecycleBoundLocationManager"

class LifecycleBoundLocationManager(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
) : LifecycleObserver {
    init {
        Log.d(TAG, "fusedLocationProviderClient add observer")
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val locationRequest = LocationRequest().apply {
        interval = fastestIntervalTime + 1
        fastestInterval = fastestIntervalTime
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        Log.d(TAG, "fusedLocationProviderClient on Resume of main activity")
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun removeLocationUpdates() {
        Log.d(TAG, "fusedLocationProviderClient on Pause of main activity")

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}