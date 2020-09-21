package com.tu.pmu.the100th.data.provider

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider

class InternetProviderImpl(val context: Context) :
    InternetProvider {
    private val TAG = "InternetProvider"
    private var isNetworkCallbackRegistered = false
    override var isNetworkConnected: Boolean = false
//        get() {
//            if(!isNetworkCallbackRegistered){
//                registerNetworkCallback()
//            }
//            return field
//        }

    init {
        registerNetworkCallback()
    }
    override fun registerNetworkCallback() {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(
                builder.build(),
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        Log.d(TAG,"Internet connection available")
                        isNetworkConnected = true
                    }

                    override fun onLost(network: Network) {
                        Log.d(TAG,"Internet connection lost")
                        isNetworkConnected = false
                    }
                }
            )
            isNetworkConnected = false
        } catch (e: Exception) {
            isNetworkConnected = false
        }
    }

}