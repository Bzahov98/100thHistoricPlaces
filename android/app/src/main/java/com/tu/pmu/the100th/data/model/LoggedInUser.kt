package com.tu.pmu.the100th.data.model

import java.time.LocalDate

data class LoggedInUser(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val displayName: String,
    val dateOfBirth : LocalDate
)
