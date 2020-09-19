package com.tu.pmu.the100th.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.data.db.entities.auth.User.Companion.CURRENT_USER_ID

@Dao
interface AuthDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User) : Long

    @Query("SELECT * FROM user WHERE email = :userEmail")
    fun getUserByEmail(userEmail: String) : LiveData<User>


    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getCurrentUser(): LiveData<User>

}
