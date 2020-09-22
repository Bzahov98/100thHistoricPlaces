package com.tu.pmu.the100th.ui.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository

class PlaceDetailViewModelFactory(
    private val preferenceProvider: PreferenceProvider,
    private val placesRepository: AllPlacesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceDetailViewModel(this.preferenceProvider, this.placesRepository) as T
    }
}