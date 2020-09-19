package com.tu.pmu.the100th.data.services

import com.tu.pmu.the100th.NationalPlacesApplication
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.network.NetworkConnectionInterceptor
import com.tu.pmu.the100th.data.network.responces.AuthResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import java.time.LocalDate

interface PlacesApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Header("Authorization") auth : String = "Basic dGVzdDp0ZXN0"
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("dateOfBirth") dateOfBirth: LocalDate = LocalDate.now(),
        @Field("nationality") nationality: String = "BG",
        @Header("Authorization") auth : String = "Basic dGVzdDp0ZXN0"
    ) : Response<AuthResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : PlacesApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(NationalPlacesApplication.getAppString(R.string.places_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlacesApi::class.java)
        }
    }

}