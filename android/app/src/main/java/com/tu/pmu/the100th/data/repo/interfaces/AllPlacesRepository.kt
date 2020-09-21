package com.tu.pmu.the100th.data.repo.interfaces

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.Content

interface AllPlacesRepository {
    val TAG: String
        get() = "AllPlacesRepository"

    suspend fun getAllPlacesByName(placeName: String)
    suspend fun getAllPlacesByLatLng(latLng: LatLng)
    suspend fun getAllPlaces() : LiveData<List<Content>>
}