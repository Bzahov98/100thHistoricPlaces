package com.tu.pmu.the100th.data.network.interceptors

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.tu.pmu.the100th.NationalPlacesApplication.Companion.getAppString
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import okhttp3.Interceptor
import okhttp3.Response


class TokenAuthenticationInterceptor(private val preferenceProvider: PreferenceProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        val sharedPreferences: SharedPreferences =
//            PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

        val accessToken = preferenceProvider.getAccessToken()

        if (accessToken == null || accessToken == getAppString(R.string.default_access_token)) {
            Log.e(
                "TokenAuthenticationInterceptor",
                "null or default access token $accessToken"
            )
        }
        val authorization = "Bearer $accessToken"
        val request = chain.request()
            .newBuilder()
            //.addHeader("Content-Type", "application/json")
            .addHeader("Authorization", authorization)
            .build()
        return chain.proceed(request)
    }

}
