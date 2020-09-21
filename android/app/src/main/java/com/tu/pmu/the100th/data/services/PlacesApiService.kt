package com.tu.pmu.the100th.data.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tu.pmu.the100th.NationalPlacesApplication
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.network.interceptors.NetworkConnectionInterceptor
import com.tu.pmu.the100th.data.network.interceptors.TokenAuthenticationInterceptor
import com.tu.pmu.the100th.data.network.responces.GetAllPlacesResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface PlacesApiService {

    @GET("places")
    fun getAllPlacesAsync(
        @Query("name") name: String? = null,
        @Query("latitude") latitude: Double? = null,
        @Query("longitude") longitude: Double? = null,
        @Query("size") size: Int = Int.MAX_VALUE
    ): Deferred<GetAllPlacesResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor,
            tokenAuthenticationInterceptor: TokenAuthenticationInterceptor
        ): PlacesApiService {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(tokenAuthenticationInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(NationalPlacesApplication.getAppString(R.string.places_api_url))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlacesApiService::class.java)
        }
    }

}