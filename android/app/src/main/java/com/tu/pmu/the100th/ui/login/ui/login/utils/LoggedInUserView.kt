package com.tu.pmu.the100th.ui.login.ui.login.utils

import java.time.LocalDate

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val firstName: String,
    val lastName: String,
    val dateOfBirth : LocalDate
    //... other data fields that may be accessible to the UI
)
