package com.tu.pmu.the100th.ui.activities.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import com.tu.pmu.the100th.ui.activities.authActivities.AuthViewModel

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(
    val internetProvider: InternetProvider,
    val locationProvider: LocationProvider,
    val preferenceProvider: PreferenceProvider
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(internetProvider,locationProvider,preferenceProvider) as T
    }
}