package com.example.spacegaze.network

import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.model.SpaceStationList
import retrofit2.http.GET
import retrofit2.http.Path

interface LaunchLibraryApiService {

    @GET("launch/upcoming?limit=10")
    suspend fun getUpcomingLaunches(): LaunchList

    @GET("spacestation/?limit=15")
    suspend fun getSpaceStations(): SpaceStationList

    @GET("launch/previous/?limit=15")
    suspend fun getPreviousLaunches(): LaunchList

    @GET("launch/{id}")
    suspend fun getLaunchById(@Path("id") id: String): Launch
}