package com.tu.pmu.the100th.data.network.dataSource.interfaces

import androidx.lifecycle.LiveData
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.network.responces.AuthSignUpResponse

interface AuthSignupNetworkDataSource {
    val downloadedAuthData: LiveData<AuthSignUpResponse>

    suspend fun fetchSignUpResponse(
        body: String
    )

    suspend fun fetchSignUpResponse(userJsonBody: SignupUserJsonBody)
    suspend fun fetchTokenResponse(accessToken: String) : Boolean
}