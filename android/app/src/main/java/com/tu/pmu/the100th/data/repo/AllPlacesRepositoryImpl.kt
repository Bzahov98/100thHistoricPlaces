package com.tu.pmu.the100th.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
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

class AllPlacesRepositoryImpl(
    private val allPlacesDataSource: GetAllPlacesNetworkDataSource,
    private val preferenceProvider: PreferenceProvider
) : AllPlacesRepository {
    lateinit var allPlaces : GetAllPlacesResponse
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

    // TODO add method to get all places data from db
    override suspend fun getAllPlaces(): LiveData<List<Content>> {
        //return allPlaces.content
        TODO("not implemented")
    }

    private fun persistFetchedAllPlacesCredentials(it: GetAllPlacesResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (it != null) {
                Log.d(
                    TAG,
                    "persistFetchedLoginCreditentals: GetAllPlacesResponse content is $it ${it.content}"
                )
                allPlaces = it
                //saveUser(User.parseAuthLoginResponse(it))
            } else Log.e(TAG, "persistFetchedLoginCreditentals: authResponse is null")
        }
    }
}