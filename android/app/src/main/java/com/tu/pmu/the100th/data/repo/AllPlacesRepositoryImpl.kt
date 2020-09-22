package com.tu.pmu.the100th.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.network.dataSource.interfaces.GetAllPlacesNetworkDataSource
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.Content
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.withTestContext

class AllPlacesRepositoryImpl(
    private val allPlacesDataSource: GetAllPlacesNetworkDataSource,
    private val preferenceProvider: PreferenceProvider
) : AllPlacesRepository {
//    lateinit var allPlaces: GetAllPlacesResponse
    override var placesEvent: MutableLiveData<GetAllPlacesResponse> = MutableLiveData()

    init {
        allPlacesDataSource.downloadedAllPlacesResponse.observeForever {
            persistFetchedAllPlacesCredentials(it)
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

    private fun persistFetchedAllPlacesCredentials(it: GetAllPlacesResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (it != null) {
                Log.d(
                    TAG,
                    "persistFetchedAllPlacesCredentials: GetAllPlacesResponse content is \n$it\n with content\n${it.content}"
                )
                placesEvent.postValue(it)
                //saveUser(User.parseAuthLoginResponse(it))
            } else Log.e(TAG, "persistFetchedAllPlacesCredentials: AllPlacesResponse is null")
        }
    }
}