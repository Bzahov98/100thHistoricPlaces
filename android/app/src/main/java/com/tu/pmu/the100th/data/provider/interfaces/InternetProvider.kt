package com.tu.pmu.the100th.data.provider.interfaces

interface InternetProvider{
    var isNetworkConnected : Boolean
    fun registerNetworkCallback()
}
