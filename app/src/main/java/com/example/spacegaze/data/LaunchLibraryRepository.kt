package com.example.spacegaze.data

import com.example.spacegaze.network.LaunchLibraryApiService

interface LaunchLibraryRepository {
    suspend fun getNextLaunch(): List<Int>
}

class NetworkLaunchLibraryRepository(
    private val LaunchLibraryApiService: LaunchLibraryApiService
) : LaunchLibraryRepository {
    override suspend fun getNextLaunch(): List<Int> {
        TODO("Not yet implemented")
    }

}