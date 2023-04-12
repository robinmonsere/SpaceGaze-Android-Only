package com.example.spacegaze.network

import com.example.spacegaze.model.LaunchList
import retrofit2.http.GET

interface LaunchLibraryApiService {

    @GET("launch/upcoming?limit=10")
    suspend fun getUpcomingLaunches(): LaunchList
}