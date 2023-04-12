package com.example.spacegaze.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spacegaze.model.Launch

@Database(entities = [Launch::class], version = 8)
abstract class LaunchDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao

    companion object {
        @Volatile
        private var instance: LaunchDatabase? = null

        fun getDatabase(context: Context): LaunchDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    LaunchDatabase::class.java,
                    "launch_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}