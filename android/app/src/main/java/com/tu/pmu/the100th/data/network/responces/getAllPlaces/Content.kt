package com.tu.pmu.the100th.data.network.responces.getAllPlaces


data class Content(
    val checked: Boolean,
    val description: String,
    val distance: Double,
    val id: String,
    val latLng: LatLng,
    val name: String
)