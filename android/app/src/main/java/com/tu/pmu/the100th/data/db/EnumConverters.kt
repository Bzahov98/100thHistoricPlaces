package com.tu.pmu.the100th.data.db

import androidx.room.TypeConverter
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.db.entities.auth.UserStatusEnum

class EnumConverters {
    @TypeConverter
    fun fromUserStatusToValue(value: UserStatusEnum): Int {
        return value.value
    }
    @TypeConverter
    fun toUserStatus(value: Int): UserStatusEnum {
        return when (value) {
            0 -> UserStatusEnum.LoggedOut
            1 -> UserStatusEnum.LoggedIn
            else -> UserStatusEnum.LoggedOut
        }
    }

}