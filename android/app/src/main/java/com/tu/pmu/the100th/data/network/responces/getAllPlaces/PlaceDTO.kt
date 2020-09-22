package com.tu.pmu.the100th.data.network.responces.getAllPlaces

import com.google.android.gms.maps.model.LatLng


data class PlaceDTO(
    val checked: Boolean,
    val description: String,
    val distance: Double,
    val id: String,
    val latLng: LatLng,
    val name: String,
    val points: Long
)