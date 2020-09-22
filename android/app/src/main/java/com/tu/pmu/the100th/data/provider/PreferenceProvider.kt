package com.tu.pmu.the100th.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.preference.PreferenceManager
import com.google.android.gms.maps.model.LatLng
import com.tu.pmu.the100th.NationalPlacesApplication
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.entities.auth.User

open class PreferenceProvider(context: Context) {
    protected val appContext: Context = context.applicationContext

    val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveAccessToken(user: User) {
        preferences.edit()
            .putString(
                NationalPlacesApplication.getAppString(R.string.preferences_key_access_token),
                user.access_token
            )
            .apply()
    }
    fun saveLoggedOutAccessToken() {
        preferences.edit()
            .putString(
                NationalPlacesApplication.getAppString(R.string.preferences_key_access_token),
                ""
            )
            .apply()
    }

    fun getAccessToken() : String =
        preferences.getString(
            NationalPlacesApplication.getAppString(R.string.preferences_key_access_token),
            NationalPlacesApplication.getAppString(R.string.default_access_token)
        )!!

    fun saveCurrentLocation(position: LatLng) {
        preferences.edit()
            .putFloat(
                NationalPlacesApplication.getAppString(R.string.current_latitude),
                position.latitude.toFloat()
            )
            .putFloat(
                NationalPlacesApplication.getAppString(R.string.current_longitude),
                position.longitude.toFloat()
            )
            .apply()
    }

    fun getCurrentPosition(): LatLng = LatLng(
        preferences.getFloat(
            NationalPlacesApplication.getAppString(R.string.current_latitude),
            0f
        ).toDouble(),
        preferences.getFloat(
            NationalPlacesApplication.getAppString(R.string.current_longitude),
            0f
        ).toDouble()
    )

    companion object {

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