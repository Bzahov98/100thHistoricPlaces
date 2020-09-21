package com.tu.pmu.the100th.data.network.dataSource.interfaces

import androidx.lifecycle.LiveData
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse

interface AuthLoginNetworkDataSource {
    val downloadedAuthData: LiveData<AuthLoginResponse>

    suspend fun fetchLoginResponse(
        email: String,
        password: String
    )

}