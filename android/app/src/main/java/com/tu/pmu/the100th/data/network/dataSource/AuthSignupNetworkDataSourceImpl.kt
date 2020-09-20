package com.tu.pmu.the100th.data.network.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthSignupNetworkDataSource
import com.tu.pmu.the100th.data.network.responces.AuthSignUpResponse
import com.tu.pmu.the100th.data.services.PlacesApiService
import com.tu.pmu.the100th.internal.utils.NoInternetException
import retrofit2.HttpException

class AuthSignupNetworkDataSourceImpl(private val placesApiService: PlacesApiService) :
    AuthSignupNetworkDataSource {

    private val TAG = "AuthSignupNetworkDataSource"
    private val downloadedAuthDataMutable = MutableLiveData<AuthSignUpResponse>()
    override val downloadedAuthData: LiveData<AuthSignUpResponse>
        get() = downloadedAuthDataMutable

    override suspend fun fetchSignUpResponse(body: String) {
        try {
            val fetchedAuth = placesApiService
                .userSignupAsync(body)
                .await()
            downloadedAuthDataMutable.postValue(fetchedAuth)

            //Log.d("TAG_connectivity", "CURRENT WEATHER: \n$fetchedAuth\n")
        } catch (ignored: NoInternetException) {
            Log.e(TAG, "No Internet Connection:")
        }catch (e: HttpException) {
            Log.e(
                TAG,
                "fetchSignUpResponse with Body with http error code: \n " +
                        "${e.code()}, message: ${e.message()}, isSuccessful: ${e.response()?.isSuccessful} "
            )
        }
    }

    override suspend fun fetchSignUpResponse(userJsonBody: SignupUserJsonBody) {
        try {
            val fetchedAuth = placesApiService
                .userSignupAsync(SignupUserJsonBody.toJson(userJsonBody))
                .await()
            downloadedAuthDataMutable.postValue(fetchedAuth)

            //Log.d("TAG_connectivity", "CURRENT WEATHER: \n$fetchedAuth\n")
        } catch (ignored: NoInternetException) {
            Log.e(TAG, "No Internet Connection:")
        }catch (e: HttpException) {
            Log.e(
                TAG,
                "fetchSignUpResponse with User with http error code:\n" +
                        " ${e.code()}, message: ${e.message()}, isSuccessful: ${e.response()?.isSuccessful} "
            )
        }
    }
}