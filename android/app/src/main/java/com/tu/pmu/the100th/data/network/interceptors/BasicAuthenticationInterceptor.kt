package com.tu.pmu.the100th.data.network.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthenticationInterceptor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorization = "Basic dGVzdDp0ZXN0"
        val request = chain.request()
            .newBuilder()
            //.addHeader("Content-Type", "application/json")
            .addHeader("Authorization", authorization)
            .build()
        return chain.proceed(request)
    }

}