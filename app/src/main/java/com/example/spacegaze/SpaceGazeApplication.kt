package com.example.spacegaze

import android.app.Application
import com.example.spacegaze.data.AppContainer
import com.example.spacegaze.data.DefaultAppContainer
import com.example.spacegaze.data.room.LaunchDatabase
import com.jakewharton.threetenabp.AndroidThreeTen


class SpaceGazeApplication : Application() {
    lateinit var container: AppContainer
    val database: LaunchDatabase by lazy { LaunchDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        AndroidThreeTen.init(this)
    }
}