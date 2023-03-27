package com.example.spacegaze.network

import retrofit2.http.GET

interface LaunchLibraryApiService {

    @GET
    suspend fun getNextLaunch(): List<Int>
}