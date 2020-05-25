package com.tu.pmu.the100th.ui.mapAllPlaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MapAllPlacesViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapAllPlacesViewModel(/*TODO add params*/) as T
    }
}
