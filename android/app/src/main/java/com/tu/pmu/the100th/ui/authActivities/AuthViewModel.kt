package com.tu.pmu.the100th.ui.authActivities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import com.tu.pmu.the100th.internal.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {
    lateinit var user: LiveData<User>

    fun getLoggedInUser() = repository.getUser()

    val userData by lazyDeferred {
        repository.getUser()
    }

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