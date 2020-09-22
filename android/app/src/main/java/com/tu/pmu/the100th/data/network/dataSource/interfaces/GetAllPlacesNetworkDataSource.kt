package com.tu.pmu.the100th.data.network.dataSource.interfaces

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.PlaceDTO

interface GetAllPlacesNetworkDataSource {
    val downloadedAllPlacesResponse: LiveData<GetAllPlacesResponse>
    val downloadedByIdPlacesResponse: LiveData<PlaceDTO>
    val errorEvent: LiveData<String>

    suspend fun fetchById(id: String, position: LatLng?)

    suspend fun fetchGetAllPlacesResponseByName(
        name: String?,
        latLng: LatLng,
        size: Int = Int.MAX_VALUE
    )

    suspend fun fetchGetAllPlacesResponseByLatLng(
        latLng: LatLng,
        size: Int = Int.MAX_VALUE
    )

    suspend fun check(placeId: String, location: LatLng)
    val downloadedAllPlacesResponseLatLng: LiveData<GetAllPlacesResponse>
}