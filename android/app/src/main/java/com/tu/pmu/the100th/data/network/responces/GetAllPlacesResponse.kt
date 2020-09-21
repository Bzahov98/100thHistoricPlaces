package com.tu.pmu.the100th.data.network.responces


import com.tu.pmu.the100th.data.network.responces.getAllPlaces.Content
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.Pageable
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.SortX

data class GetAllPlacesResponse(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)