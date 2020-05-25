package com.tu.pmu.the100th.data.network

import com.tu.pmu.the100th.data.Result
import com.tu.pmu.the100th.data.model.LoggedInUser
import java.io.IOException
import java.time.LocalDate

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane", "Doe", username,
                LocalDate.now())
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(
                IOException(
                    "Error logging in",
                    e
                )
            )
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

