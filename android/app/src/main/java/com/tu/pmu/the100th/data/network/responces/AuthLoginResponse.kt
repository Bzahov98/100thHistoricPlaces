package com.tu.pmu.the100th.data.network.responces


import com.google.gson.annotations.SerializedName

data class AuthLoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    val email: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    val jti: String,
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("user_id")
    val userId: String
)