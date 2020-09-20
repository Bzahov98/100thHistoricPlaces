package com.tu.pmu.the100th.data.db.entities.auth

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.tu.pmu.the100th.data.network.responces.AuthLoginResponse


@Entity
data class User(
    var userStatus: UserStatusEnum = UserStatusEnum.LoggedOut,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    @SerializedName("expires_in")
    var expiresIn: String? = null,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("token_type")
    var access_token: String? = null
) {


    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID

    companion object {
//        fun userFromResponse(user: AuthLoginResponse) : User{
//            return User(UserStatusEnum.LoggedOut,user.)
//        }

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

        fun parseAuthLoginResponse(it: AuthLoginResponse): User {
            return User(
                UserStatusEnum.LoggedOut,
                //REWORK: name is missing from data received from server
                "NO_DATA",
                it.email,
                "No_Data",
                it.expiresIn.toString(),
                it.userId,
                it.accessToken
            )
        }

        const val CURRENT_USER_ID = 0

    }
}
