package com.tu.pmu.the100th.ui.AuthActivities

import androidx.lifecycle.ViewModel
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String,
        name: String = "test" // ss
    ) = withContext(Dispatchers.IO) { repository.userLogin(email, password, name) }

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userSignup(name, email, password) }

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)

}