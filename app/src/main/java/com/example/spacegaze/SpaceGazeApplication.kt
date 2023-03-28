package com.example.spacegaze

import android.app.Application
import com.example.spacegaze.data.AppContainer
import com.example.spacegaze.data.DefaultAppContainer
import com.jakewharton.threetenabp.AndroidThreeTen


class SpaceGazeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        AndroidThreeTen.init(this)
    }
}