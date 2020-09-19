package com.tu.pmu.the100th.data.repo.interfaces

import androidx.lifecycle.LiveData
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.network.responces.AuthResponse

interface UserRepository {
    suspend fun userLogin(email: String, password: String, name: String): AuthResponse

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ): AuthResponse

    suspend fun saveUser(user: User) : Long
    fun getUser() : LiveData<User>
    suspend fun logout()
}