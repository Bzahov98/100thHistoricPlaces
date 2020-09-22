package com.tu.pmu.the100th.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.network.dataSource.interfaces.GetAllPlacesNetworkDataSource
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.PlaceDTO
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AllPlacesRepositoryImpl(
    private val allPlacesDataSource: GetAllPlacesNetworkDataSource,
    private val preferenceProvider: PreferenceProvider
) : AllPlacesRepository {
    lateinit var allPlaces: GetAllPlacesResponse
    override var placesEvent: MutableLiveData<GetAllPlacesResponse> = MutableLiveData()
    override var placeByIdEvent: MutableLiveData<PlaceDTO> = MutableLiveData()
    override val errorEvent: LiveData<String>
        get() = allPlacesDataSource.errorEvent

    init {
        allPlacesDataSource.downloadedAllPlacesResponse.observeForever {
            persistFetchedAllPlacesCredentials(it)
        }
        allPlacesDataSource.downloadedByIdPlacesResponse.observeForever {
            persistFetchedByIdPlacesCredentials(it)
        }
    }

    override suspend fun getAllPlacesByName(placeName: String) {
        allPlacesDataSource.fetchGetAllPlacesResponseByName(
            placeName
        )
        // TODO save to db
    }

    override suspend fun getAllPlacesByLatLng(latLng: LatLng) {
        allPlacesDataSource.fetchGetAllPlacesResponseByLatLng(
            latLng
        )
        // TODO save to db
    }

    override suspend fun getById(id: String, position: LatLng?) {
        allPlacesDataSource.fetchById(id, position);
    }

    override suspend fun check(id: String, position: LatLng) {
        allPlacesDataSource.check(id, position);
    }

    private fun persistFetchedAllPlacesCredentials(it: GetAllPlacesResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (it != null) {
                Log.d(
                    TAG,
                    "persistFetchedLoginCreditentals: GetAllPlacesResponse content is $it ${it.content}"
                )
                placesEvent.postValue(it)
                //saveUser(User.parseAuthLoginResponse(it))
            } else Log.e(TAG, "persistFetchedLoginCreditentals: authResponse is null")
        }
    }

    private fun persistFetchedByIdPlacesCredentials(it: PlaceDTO?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (it != null) {
                Log.d(
                    TAG,
                    "persistFetchedLoginCreditentals: GetAllPlacesResponse content is $it ${it}"
                )
                placeByIdEvent.postValue(it)
                //saveUser(User.parseAuthLoginResponse(it))
            } else Log.e(TAG, "persistFetchedLoginCreditentals: authResponse is null")
        }
    }

}