package com.tu.pmu.the100th.ui.login.ui.login.utils

import com.tu.pmu.the100th.ui.login.ui.login.utils.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)
