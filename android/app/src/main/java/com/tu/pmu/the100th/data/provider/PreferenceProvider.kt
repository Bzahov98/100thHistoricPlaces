package com.tu.pmu.the100th.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.preference.PreferenceManager
import com.tu.pmu.the100th.NationalPlacesApplication
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.entities.auth.User

open class PreferenceProvider(context: Context) {
    protected val appContext: Context = context.applicationContext

    val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveAccessToken(user: User) {
        preferences.edit()
            .putString(NationalPlacesApplication.getAppString(R.string.preferences_key_access_token), user.access_token)
            .apply()
    }

    fun getAccessToken() =
        preferences.getString(
            NationalPlacesApplication.getAppString(R.string.preferences_key_access_token),
            NationalPlacesApplication.getAppString(R.string.default_access_token)
        )

    companion object{

        fun getFromSharedPreferenceStringToBoolean(
            context: Context,
            preferenceKey: String,
            defaultValue: String
        ): Boolean {
            val currentPreferenceValue = getSharedPreferences(context)
                .getString(preferenceKey, defaultValue)

            return currentPreferenceValue == defaultValue
        }

        fun getFromSharedPreferenceBoolean(
            context: Context,
            preferenceKey: String,
            defaultValue: Boolean
        ): Boolean {

            return getSharedPreferences(context)
                .getBoolean(preferenceKey, defaultValue)
        }

        fun getSharedPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)


    }

}