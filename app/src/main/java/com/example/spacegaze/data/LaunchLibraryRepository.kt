package com.example.spacegaze.data

import com.example.spacegaze.model.Launch
import com.example.spacegaze.network.LaunchLibraryApiService

interface LaunchLibraryRepository {
    suspend fun getNextLaunch(): Launch
}

class NetworkLaunchLibraryRepository(
    private val LaunchLibraryApiService: LaunchLibraryApiService
) : LaunchLibraryRepository {
    override suspend fun getNextLaunch(): Launch = LaunchLibraryApiService.getNextLaunch()

}