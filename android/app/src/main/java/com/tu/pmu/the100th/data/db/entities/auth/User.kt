package com.tu.pmu.the100th.data.db.entities.auth

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    var userStatus: UserStatusEnum = UserStatusEnum.LoggedOut,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var email_verified_at: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null
) {

    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID

    companion object {
        fun loggedOutUser(): User {
            return User(
                UserStatusEnum.LoggedOut,
                "LoggedOutUser",
                "LoggedOutUser",
                "LoggedOutUser",
                "LoggedOutUser",
                "LoggedOutUser",
                "LoggedOutUser"
            )
        }

        const val CURRENT_USER_ID = 0

    }
}
