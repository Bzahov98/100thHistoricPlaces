package com.tu.pmu.the100th.data.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tu.pmu.the100th.NationalPlacesApplication
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.network.interceptors.BasicAuthenticationInterceptor
import com.tu.pmu.the100th.data.network.interceptors.NetworkConnectionInterceptor
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse
import com.tu.pmu.the100th.data.network.responces.AuthSignUpResponse
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

    /*@FormUrlEncoded
    @Headers("Content-Type: application/json",)
    @POST("users/signup")
    fun userSignupAsync(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("nationality") nationality: String = "BG",
        @Field("password") password: String,
        @Header("Authorization") auth : String = "Basic dGVzdDp0ZXN0",*//*
        @Header("Accept") accept : String = "application/json",
        @Header("Accept-Encoding") acceptEnc : String = "gzip, deflate, br",
        @Header("Connection") con : String = "keep-alive"*//*
    ) : Deferred<AuthSignUpResponse>*/
//    @POST("/urlPath")
//    @FormUrlEncoded
//    De<Response> myApi(@Header("Authorization") String auth, @Header("KEY") String key,
//    @Body JsonObject/POJO/String requestBody);

    @Headers("Content-Type: application/json")
    @POST("users/signup")
    fun userSignupAsync(@Body user: String): Deferred<AuthSignUpResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            basicAuthenticationInterceptor: BasicAuthenticationInterceptor
        ): AuthApiService {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(basicAuthenticationInterceptor)
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