package com.tu.pmu.the100th.ui.AuthActivities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}