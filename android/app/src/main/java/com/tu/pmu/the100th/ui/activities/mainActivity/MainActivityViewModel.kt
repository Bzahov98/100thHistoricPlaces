package com.tu.pmu.the100th.ui.activities.mainActivity

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository

class MainActivityViewModel(
    val internetProvider: InternetProvider,
    val locationProvider: LocationProvider,
    val preferenceProvider: PreferenceProvider
) : ViewModel() {

    suspend fun saveLastKnownLocation() {
        locationProvider.getLastPhysicalDeviceLocation()?.let { location ->
            preferenceProvider.saveCurrentLocation(LatLng(location.latitude, location.longitude))
        }
    }
}