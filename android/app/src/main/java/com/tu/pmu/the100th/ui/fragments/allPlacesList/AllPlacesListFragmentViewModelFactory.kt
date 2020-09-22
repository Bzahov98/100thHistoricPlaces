package com.tu.pmu.the100th.ui.fragments.allPlacesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository

class AllPlacesListFragmentViewModelFactory(
private val allPlacesRepository: AllPlacesRepository,
private val internetProvider: InternetProvider,
private val locationProvider: LocationProvider,
private val preferenceProvider: PreferenceProvider
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AllPlacesListFragmentViewModel(
            allPlacesRepository,
            internetProvider,
            locationProvider,
            preferenceProvider
        ) as T
    }
}
