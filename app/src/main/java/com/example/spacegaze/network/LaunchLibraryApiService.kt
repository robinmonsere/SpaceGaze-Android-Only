package com.example.spacegaze.network

import androidx.compose.ui.geometry.Offset
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.model.SpaceStationList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LaunchLibraryApiService {

    @GET("launch/upcoming")
    suspend fun getUpcomingLaunches(@Query("limit") limit: Int, @Query("offset") offset: Int): LaunchList

    @GET("spacestation/?limit=15")
    suspend fun getSpaceStations(): SpaceStationList

    @GET("launch/previous/?limit=15")
    suspend fun getPreviousLaunches(): LaunchList

    @GET("launch/{id}")
    suspend fun getLaunchById(@Path("id") id: String): Launch

}