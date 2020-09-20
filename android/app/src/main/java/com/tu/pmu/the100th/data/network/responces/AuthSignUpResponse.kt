package com.tu.pmu.the100th.data.network.responces


import com.google.gson.annotations.SerializedName

data class AuthSignUpResponse(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val nationality: String,
    val points: Int,
    val status: String,
    val errorCode: String?,
    val message: String?
)