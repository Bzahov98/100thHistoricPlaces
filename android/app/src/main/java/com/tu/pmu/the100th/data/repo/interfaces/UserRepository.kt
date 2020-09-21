package com.tu.pmu.the100th.data.repo.interfaces

import androidx.lifecycle.LiveData
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse
import com.tu.pmu.the100th.data.network.responces.AuthResponse
import com.tu.pmu.the100th.data.network.responces.AuthSignUpResponse

interface UserRepository {
    suspend fun userLogin(email: String, password: String, name: String): LiveData<User>

//
    suspend fun userSignup(
        newUser: SignupUserJsonBody
    )

    suspend fun saveUser(user: User) : Long
    fun getLoggedInUser() : LiveData<User>
    fun getLastSignUpResponse(): LiveData<AuthSignUpResponse>
    suspend fun logout()

   // suspend fun userSignup(
//        name: String,
//        email: String,
//        password: String
//    ): AuthResponse

}