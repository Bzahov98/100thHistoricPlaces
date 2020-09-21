package com.tu.pmu.the100th.ui.fragments.allPlaces

import androidx.lifecycle.ViewModel
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider

class AllPlacesMapFragmentViewModel(
    val internetProvider: InternetProvider,
    val locationProvider: LocationProvider,
    val preferenceProvider: PreferenceProvider
) : ViewModel(){

}