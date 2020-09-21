package com.tu.pmu.the100th.data.network.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthLoginNetworkDataSource
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse
import com.tu.pmu.the100th.data.services.AuthApiService
import com.tu.pmu.the100th.internal.utils.NoInternetException
import retrofit2.HttpException

class AuthLoginNetworkDataSourceImpl(private val authApiService: AuthApiService) :
    AuthLoginNetworkDataSource {
    private val TAG = "AuthLoginNetworkDataSource"
    private val downloadedAuthDataMutable = MutableLiveData<AuthLoginResponse>()
    override val downloadedAuthData: LiveData<AuthLoginResponse>
        get() = downloadedAuthDataMutable


    override suspend fun fetchLoginResponse(email: String, password: String) {
        try {
            val fetchedAuth = authApiService
                .userLoginAsync(email, password)
                .await()
            downloadedAuthDataMutable.postValue(fetchedAuth)

            //Log.d("TAG_connectivity", "CURRENT WEATHER: \n$fetchedAuth\n")
        } catch (ignored: NoInternetException) {
            Log.e(TAG, "No Internet Connection:")
        } catch (e: HttpException) {
            Log.e(
                TAG,
                "fetchCurrentWeather with http error code:\n " +
                        "${e.code()}, message: ${e.message()}, isSuccessful: ${e.response()?.isSuccessful} ${e.response()?.message()} "
            )
        }


    }


}