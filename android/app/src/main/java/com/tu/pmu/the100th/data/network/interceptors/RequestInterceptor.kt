package com.tu.pmu.the100th.data.network.interceptors

import android.util.Log
import okhttp3.Interceptor

class RequestInterceptor() {
    companion object {

        private val TAG = "RequestInterceptor"
        fun getRequestWithParameter(queryParameterName: String, paramValue: String): Interceptor =
            Interceptor {
                val url = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(queryParameterName, paramValue)
                    .build()

                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()

                Log.d(TAG, "Invoke() of get Weather with url: $url,\nand request $request\n")

                return@Interceptor it.proceed(request)
            }
    }
}