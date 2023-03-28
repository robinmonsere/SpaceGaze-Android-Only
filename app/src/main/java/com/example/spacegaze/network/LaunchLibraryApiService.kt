package com.example.spacegaze.network

import com.example.spacegaze.model.Launch
import retrofit2.http.GET

interface LaunchLibraryApiService {

    @GET("launch/upcoming?mode=list&limit=1")
    suspend fun getNextLaunch(): Launch
}