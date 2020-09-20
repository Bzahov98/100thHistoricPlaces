package com.tu.pmu.the100th.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tu.pmu.the100th.data.db.dao.AuthDao
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.db.entities.auth.UserStatusEnum
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthLoginNetworkDataSource
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthSignupNetworkDataSource
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse
import com.tu.pmu.the100th.data.network.responces.AuthResponse
import com.tu.pmu.the100th.data.network.responces.AuthSignUpResponse
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    val userDao: AuthDao,
    private val loginDataSource: AuthLoginNetworkDataSource,
    private val signUpDataSource: AuthSignupNetworkDataSource
) : UserRepository {
    private val TAG = "UserRepository"
    var savedUser: MutableLiveData<User> = MutableLiveData()

    init {
        loginDataSource.downloadedAuthData.observeForever {
            persistFetchedLoginCredentials(it)
        }
        signUpDataSource.downloadedAuthData.observeForever {
            persistFetchedSignUpCredentials(it)
        }
    }

    private fun persistFetchedSignUpCredentials(it: AuthSignUpResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (it != null) {
                Log.d(
                    TAG,
                    "persistFetchedLoginCreditentals: authResponse for registered\n userID:${it.id}\n email: ${it.email}"
                )
            } else Log.e(TAG, "persistFetchedSignUpCreditentals: authResponse is null")
        }
    }

    private fun persistFetchedLoginCredentials(it: AuthLoginResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (it != null) {
                Log.d(
                    TAG,
                    "persistFetchedLoginCreditentals: authResponse is ${it.toString()} ${it.email}"
                )
                saveUser(User.parseAuthLoginResponse(it))
            } else Log.e(TAG, "persistFetchedLoginCreditentals: authResponse is null")
        }
    }

    override suspend fun userLogin(
        email: String,
        password: String,
        name: String
    ): LiveData<User> {
        //Log.d("UserRepositoryImpl", "userLogin TEST IS OK")
        fetchUserLogin(email, password)
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getCurrentUser()
        }
//        return AuthResponse(
//            true,
//            "TEST",
//            User(UserStatusEnum.LoggedOut, name, email, password, "test", "test", "test")
//        )
    }

    private suspend fun fetchUserLogin(email: String, password: String) {
        loginDataSource.fetchLoginResponse(
            email,
            password
        )
    }

    override suspend fun userSignup(name: String, email: String, password: String): AuthResponse {
        // TODO fetch data from api
        Log.d("UserRepositoryImpl", " userSignup TEST IS OK")
        return AuthResponse(
            true,
            "TEST",
            User(UserStatusEnum.LoggedOut, name, email, password, "test", "test", "test")
        )
    }

    // TODO put it in DB
    override suspend fun saveUser(user: User): Long {
        Log.d("UserRepositoryImpl", "saveUser TEST IS OK")
        user.userStatus = UserStatusEnum.LoggedIn
        savedUser.postValue(user)
        return userDao.upsert(user)
    }

    override fun getUser(): LiveData<User> {
        Log.d("UserRepositoryImpl", "getUser TEST IS OK")
        return userDao.getCurrentUser()
    }

    override suspend fun logout() {
        val user = User.loggedOutUser()
        userDao.upsert(user)
        savedUser.postValue(user)
    }

}