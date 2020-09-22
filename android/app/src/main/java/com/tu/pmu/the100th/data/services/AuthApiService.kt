package com.tu.pmu.the100th.data.services

import com.grapesnberries.curllogger.CurlLoggerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tu.pmu.the100th.NationalPlacesApplication
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.network.interceptors.BasicAuthenticationInterceptor
import com.tu.pmu.the100th.data.network.interceptors.NetworkConnectionInterceptor
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse
import com.tu.pmu.the100th.data.network.responces.AuthSignUpResponse
import com.tu.pmu.the100th.data.network.responces.CheckTokenResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface AuthApiService {

    @FormUrlEncoded
    @POST("oauth/token?grant_type=password")
    fun userLoginAsync(
        @Field("username") email: String,
        @Field("password") password: String/*,
        @Header("Authorization") auth: String = "Basic dGVzdDp0ZXN0"*/
    ): Deferred<AuthLoginResponse>

    @Headers("Content-Type: application/json")
    @POST("users/signup")
    fun userSignupAsync(@Body user: String): Deferred<AuthSignUpResponse>

    @GET("oauth/check_token")
    fun checkAccessTokenAsync(
        @Query("token") token:String
    ): Deferred<CheckTokenResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            basicAuthenticationInterceptor: BasicAuthenticationInterceptor
        ): AuthApiService {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(basicAuthenticationInterceptor)
                .addInterceptor(CurlLoggerInterceptor("Curl request"))
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(NationalPlacesApplication.getAppString(R.string.places_api_url))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthApiService::class.java)
        }
    }

}