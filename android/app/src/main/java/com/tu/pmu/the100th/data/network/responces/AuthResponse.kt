package com.tu.pmu.the100th.data.network.responces

import com.tu.pmu.the100th.data.db.entities.auth.User

data class AuthResponse(
    val isSuccessful : Boolean = false,
    val message: String?,
    val user: User?
)