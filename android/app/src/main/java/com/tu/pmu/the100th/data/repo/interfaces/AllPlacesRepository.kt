package com.tu.pmu.the100th.data.repo.interfaces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.PlaceDTO

interface AllPlacesRepository {
    val TAG: String
        get() = "AllPlacesRepository"

    var placesEvent: MutableLiveData<GetAllPlacesResponse>
    var placeByIdEvent: MutableLiveData<PlaceDTO>
    val errorEvent: LiveData<String>

    suspend fun getAllPlacesByName(placeName: String)
    suspend fun getAllPlacesByLatLng(latLng: LatLng)
    suspend fun check(id: String, position: LatLng)

    suspend fun getById(id: String, position: LatLng?)
}