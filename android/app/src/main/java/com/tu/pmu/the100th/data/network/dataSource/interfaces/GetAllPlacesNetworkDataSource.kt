package com.tu.pmu.the100th.data.network.dataSource.interfaces

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import retrofit2.http.Query

interface GetAllPlacesNetworkDataSource {
    val downloadedAllPlacesResponse: LiveData<GetAllPlacesResponse>

    suspend fun fetchGetAllPlacesResponseByName(
        name: String?,
        size: Int = Int.MAX_VALUE
    )

    suspend fun fetchGetAllPlacesResponseByLatLng(
        latLng: LatLng,
        size: Int = Int.MAX_VALUE
    )
}