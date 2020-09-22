package com.tu.pmu.the100th.data.network.dataSource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.network.dataSource.interfaces.GetAllPlacesNetworkDataSource
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.PlaceDTO
import com.tu.pmu.the100th.data.services.PlacesApiService
import com.tu.pmu.the100th.internal.utils.NoInternetException
import retrofit2.HttpException

class GetAllPlacesNetworkDataSourceImpl(private val allPlacesApiService: PlacesApiService) :
    GetAllPlacesNetworkDataSource {
    private val TAG = "GetAllPlacesNetworkDataSource"
    private val downloadedAllPlacesResponseMutable = MutableLiveData<GetAllPlacesResponse>()
    override val downloadedAllPlacesResponse: LiveData<GetAllPlacesResponse>
        get() = downloadedAllPlacesResponseMutable

    private val downloadedAllPlacesResponseLatLngMutable = MutableLiveData<GetAllPlacesResponse>()
    override val downloadedAllPlacesResponseLatLng: LiveData<GetAllPlacesResponse>
        get() = downloadedAllPlacesResponseLatLngMutable


    override val downloadedByIdPlacesResponse: LiveData<PlaceDTO>
        get() = downloadedByIdPlacesResponseMutable

    override val errorEvent: LiveData<String>
        get() = errorEventData

    private val downloadedByIdPlacesResponseMutable = MutableLiveData<PlaceDTO>()
    private val errorEventData = MutableLiveData<String>()

    override suspend fun fetchGetAllPlacesResponseByName(name: String?, latLng: LatLng, size: Int) {
        try {
            val fetchedPlaces = allPlacesApiService
                .getAllPlacesAsync(name, latLng.latitude, latLng.longitude)
                .await()
            downloadedAllPlacesResponseMutable.postValue(fetchedPlaces)
            Log.e(TAG, "CURRENT WEATHER: \n${fetchedPlaces.content}\n")
        } catch (ignored: NoInternetException) {
            Log.e(TAG, "No Internet Connection:")
        } catch (e: HttpException) {
            Log.e(
                TAG,
                "fetchSignUpResponse with Body with http error code: \n " +
                        "${e.code()}, message: ${e.message()}, isSuccessful: ${e.response()?.isSuccessful} "
            )
        }
    }

    override suspend fun fetchGetAllPlacesResponseByLatLng(latLng: LatLng, size: Int) {
        try {
            val fetchedPlaces = allPlacesApiService
                .getAllPlacesAsync(null, latLng.latitude, latLng.longitude)
                .await()
            downloadedAllPlacesResponseMutable.postValue(fetchedPlaces)
            Log.e(TAG, "CURRENT WEATHER: \n${fetchedPlaces.content}\n")
        } catch (ignored: NoInternetException) {
            Log.e(TAG, "No Internet Connection:")
        } catch (e: HttpException) {
            Log.e(
                TAG,
                "fetchSignUpResponse with Body with http error code: \n " +
                        "${e.code()}, message: ${e.message()}, isSuccessful: ${e.response()?.isSuccessful} "
            )
        }
    }

    override suspend fun fetchById(id: String, position: LatLng?) {
        try {
            val fetchedPlaces = allPlacesApiService
                .getByIdAsync(id, position?.latitude, position?.longitude)
                .await()
            downloadedByIdPlacesResponseMutable.postValue(fetchedPlaces)
            Log.e(TAG, "fetchById: \n${fetchedPlaces}\n")
        } catch (ignored: NoInternetException) {
            Log.e(TAG, "No Internet Connection:")
        } catch (e: HttpException) {
            Log.e(
                TAG,
                "fetchById with Body with http error code: \n " +
                        "${e.code()}, message: ${e.message()}, isSuccessful: ${e.response()?.isSuccessful} "
            )
        }
    }


    override suspend fun check(placeId: String, location: LatLng) {
        val checkedResponse =
            allPlacesApiService.check(placeId, location).await()

        if (!checkedResponse.isSuccessful)
            errorEventData.postValue(checkedResponse.errorBody().toString())
    }
}