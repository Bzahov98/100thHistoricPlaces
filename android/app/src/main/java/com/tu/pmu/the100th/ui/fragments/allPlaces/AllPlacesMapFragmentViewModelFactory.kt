package com.tu.pmu.the100th.ui.fragments.allPlaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider

class AllPlacesMapFragmentViewModelFactory(
    private val internetProvider: InternetProvider,
    private val locationProvider: LocationProvider,
    private val preferenceProvider: PreferenceProvider
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AllPlacesMapFragmentViewModelFactory(internetProvider, locationProvider,preferenceProvider) as T
    }
}