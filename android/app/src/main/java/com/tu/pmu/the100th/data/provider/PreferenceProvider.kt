package com.tu.pmu.the100th.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.preference.PreferenceManager

open class PreferenceProvider(context: Context) {
    protected val appContext: Context = context.applicationContext

    val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

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