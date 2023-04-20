package com.example.spacegaze.network

import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.model.SpaceStationList
import retrofit2.http.GET

interface LaunchLibraryApiService {

    @GET("launch/upcoming?limit=10")
    suspend fun getUpcomingLaunches(): LaunchList

    @GET("spacestation/?limit=15")
    suspend fun getSpaceStations(): SpaceStationList
}