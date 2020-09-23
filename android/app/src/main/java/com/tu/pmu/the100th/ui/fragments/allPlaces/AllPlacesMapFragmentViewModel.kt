package com.tu.pmu.the100th.ui.fragments.allPlaces

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository
import com.tu.pmu.the100th.internal.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AllPlacesMapFragmentViewModel(
    val allPlacesRepository: AllPlacesRepository,
    val internetProvider: InternetProvider,
    val locationProvider: LocationProvider,
    val preferenceProvider: PreferenceProvider
) : ViewModel() {

    val placesEvent: MutableLiveData<GetAllPlacesResponse>
        get() = allPlacesRepository.placesEvent

    //val allPlaces2 = allPlacesRepository.getAllPlaces2()

    suspend fun getAllPlacesRequest(
        latLng: LatLng
    ) = withContext(Dispatchers.IO) {
            allPlacesRepository.getAllPlacesByLatLng(latLng)
    }

    suspend fun getLastLocationLatLng(): LatLng {
        val location = locationProvider.getLastPhysicalDeviceLocation()
        if(location == null){
            Log.e("AllPlacesMapFragmentViewModel", "getLastLocationLatLng: is null")
            return LatLng(
                42.6847251,23.3189384
            )
        }
        return LatLng(
            location.latitude,location.longitude
        )
    }

    suspend fun getAllPlacesRequestByName(
        name: String,
        latLng: LatLng
    ) = withContext(Dispatchers.IO) {
        allPlacesRepository.getAllPlacesByName(name, latLng)
    }

}