package com.tu.pmu.the100th.data.repo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tu.pmu.the100th.data.db.dao.AuthDao
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.db.entities.auth.UserStatusEnum
import com.tu.pmu.the100th.data.network.responces.AuthResponse
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository

class UserRepositoryImpl(val userDao: AuthDao) : UserRepository{
    var savedUser : MutableLiveData<User> = MutableLiveData()

    override suspend fun userLogin(email: String, password: String, name :String): AuthResponse {
        // TODO fetch data from api
        Log.d("UserRepositoryImpl", "userLogin TEST IS OK")

        return AuthResponse(true,"TEST", User( UserStatusEnum.LoggedOut,name, email,password,"test","test","test"))
    }

    override suspend fun userSignup(name: String, email: String, password: String): AuthResponse {
        // TODO fetch data from api
        Log.d("UserRepositoryImpl", " userSignup TEST IS OK")
        return AuthResponse(true,"TEST", User( UserStatusEnum.LoggedOut,name, email,password,"test","test","test"))
    }
    // TODO put it in DB
    override suspend fun saveUser(user: User): Long {
        Log.d("UserRepositoryImpl", "saveUser TEST IS OK")
        user.userStatus= UserStatusEnum.LoggedIn
        savedUser.postValue(user)
        return userDao.upsert(user)
    }

    override fun getUser(): LiveData<User> {
        Log.d("UserRepositoryImpl", "getUser TEST IS OK")
         return userDao.getCurrentUser()
    }

    override suspend fun logout() {
        userDao.upsert(User.loggedOutUser())
    }

}