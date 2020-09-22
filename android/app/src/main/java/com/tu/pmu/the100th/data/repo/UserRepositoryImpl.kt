package com.tu.pmu.the100th.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.NationalPlacesApplication.Companion.getAppString
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.dao.AuthDao
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.db.entities.auth.UserStatusEnum
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthLoginNetworkDataSource
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthSignupNetworkDataSource
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse
import com.tu.pmu.the100th.data.network.responces.AuthResponse
import com.tu.pmu.the100th.data.network.responces.AuthSignUpResponse
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    val userDao: AuthDao,
    private val loginDataSource: AuthLoginNetworkDataSource,
    private val signUpDataSource: AuthSignupNetworkDataSource,
    private val preferenceProvider: PreferenceProvider,
    private val locationProvider: LocationProvider
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

    private fun persistFetchedLoginCredentials(it: AuthLoginResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (it != null) {
                Log.d(
                    TAG,
                    "persistFetchedLoginCreditentals: authResponse is $it ${it.email}"
                )
                saveUser(User.parseAuthLoginResponse(it))
            } else Log.e(TAG, "persistFetchedLoginCreditentals: authResponse is null")
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

    override suspend fun userLogin(
        email: String,
        password: String,
        name: String
    ): LiveData<User> {
        loginDataSource.fetchLoginResponse(
            email,
            password
        )
        return withContext(Dispatchers.IO) {
            return@withContext userDao.getCurrentUser()
        }
    }

    override suspend fun userSignup(newUser: SignupUserJsonBody) {
        signUpDataSource.fetchSignUpResponse(
            newUser
        )
    }

    override suspend fun checkIsValidToken() : Boolean {
        return signUpDataSource.fetchTokenResponse(
            preferenceProvider.getAccessToken()
        )
    }

    override suspend fun saveUser(user: User): Long {
//        Log.d("UserRepositoryImpl", "saveUser TEST IS OK")
        user.userStatus = UserStatusEnum.LoggedIn
        savedUser.postValue(user)
        preferenceProvider.saveAccessToken(user)
        locationProvider.getLastPhysicalDeviceLocation()?.let { location ->
            preferenceProvider.saveCurrentLocation(LatLng(location.latitude, location.longitude))
        }
        return userDao.upsert(user)
    }

    override fun getLoggedInUser(): LiveData<User> {
//        Log.d("UserRepositoryImpl", "getUser TEST IS OK")
        return userDao.getCurrentUser()
    }

    override fun getLastSignUpResponse(): LiveData<AuthSignUpResponse> {
//        Log.d("UserRepositoryImpl", "getUser TEST IS OK")
        return signUpDataSource.downloadedAuthData
    }

    override suspend fun logout() {
        val user = User.loggedOutUser()
        userDao.upsert(user)
        preferenceProvider.saveLoggedOutAccessToken()
        savedUser.postValue(user)
    }

}