package com.tu.pmu.the100th.ui.fragments.allPlacesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AllPlacesListFragmentViewModel(
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

    suspend fun getAllPlacesRequestByName(
        name: String,
        latLng: LatLng
    ) = withContext(Dispatchers.IO) {
        allPlacesRepository.getAllPlacesByName(name, latLng)
    }

    suspend fun getLastLocationLatLng(): LatLng {
        val location = locationProvider.getLastPhysicalDeviceLocation()!!
        return LatLng(
            location.latitude,location.longitude
        )
    }

}