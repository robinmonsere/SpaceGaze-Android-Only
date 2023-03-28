package com.example.spacegaze.data

import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.network.LaunchLibraryApiService

interface LaunchLibraryRepository {
    suspend fun getNextLaunch(): LaunchList
}

class NetworkLaunchLibraryRepository(
    private val LaunchLibraryApiService: LaunchLibraryApiService
) : LaunchLibraryRepository {
    override suspend fun getNextLaunch(): LaunchList = LaunchLibraryApiService.getNextLaunch()

}