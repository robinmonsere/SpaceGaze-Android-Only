package com.example.spacegaze.data

import com.example.spacegaze.network.LaunchLibraryApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val launchLibraryRepository: LaunchLibraryRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://lldev.thespacedevs.com/2.2.0/"

    val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: LaunchLibraryApiService by lazy {
        retrofit.create(LaunchLibraryApiService::class.java)
    }

    override val launchLibraryRepository: LaunchLibraryRepository by lazy {
        NetworkLaunchLibraryRepository(retrofitService)
    }
}