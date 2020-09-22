package com.tu.pmu.the100th.data.repo.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.Content

interface AllPlacesRepository {
    val TAG: String
        get() = "AllPlacesRepository"

    var placesEvent : MutableLiveData<GetAllPlacesResponse>

    suspend fun getAllPlacesByName(placeName: String)
    suspend fun getAllPlacesByLatLng(latLng: LatLng)
}