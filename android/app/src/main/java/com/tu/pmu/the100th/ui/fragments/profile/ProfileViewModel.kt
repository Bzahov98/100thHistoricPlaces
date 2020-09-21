package com.tu.pmu.the100th.ui.fragments.profile

import androidx.lifecycle.ViewModel;
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getLoggedInUser()
    var user = repository.getLoggedInUser()
    fun logOut(){
        CoroutineScope(Dispatchers.IO).launch{
            repository.logout()
        }
    }
}
