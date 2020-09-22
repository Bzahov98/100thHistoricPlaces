package com.tu.pmu.the100th.data.network.responces


import com.google.gson.annotations.SerializedName

data class CheckTokenResponse(
    val active: Boolean,
    @SerializedName("client_id")
    val clientId: String,
    val email: String,
    val exp: Int,
    val jti: String,
    val scope: List<String>,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String
)