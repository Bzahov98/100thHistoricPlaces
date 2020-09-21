package com.tu.pmu.the100th.data.network.dataSource.interfaces

import androidx.lifecycle.LiveData
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse
import com.tu.pmu.the100th.data.services.PlacesApiService

interface AuthLoginNetworkDataSource {
    val downloadedAuthData: LiveData<AuthLoginResponse>

    suspend fun fetchLoginResponse(
        email: String,
        password: String
    )

}