package com.tu.pmu.the100th.data.db

import android.content.Context
import androidx.room.*
import com.tu.pmu.the100th.data.db.dao.AuthDao
import com.tu.pmu.the100th.data.db.entities.auth.User

@Database(
    entities = [User::class],
    version = 3
)
@TypeConverters(EnumConverters::class)
abstract class PlacesDatabase : RoomDatabase() {

    abstract fun getUserDao(): AuthDao

    companion object {

        @Volatile
        private var instance: PlacesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PlacesDatabase::class.java,
                "NationalPlacesDatabase.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}