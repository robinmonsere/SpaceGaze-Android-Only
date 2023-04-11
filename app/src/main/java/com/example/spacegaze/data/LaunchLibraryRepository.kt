package com.example.spacegaze.data

import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.network.LaunchLibraryApiService

interface LaunchLibraryRepository {
    suspend fun getUpcomingLaunches(): LaunchList
}

class NetworkLaunchLibraryRepository(
    private val LaunchLibraryApiService: LaunchLibraryApiService
) : LaunchLibraryRepository {
    override suspend fun getUpcomingLaunches(): LaunchList {
        val upcomingLaunches = LaunchLibraryApiService.getUpcomingLaunches()
        for (launch in upcomingLaunches.launches) {
            launch.isUpcoming = true
        }
        return upcomingLaunches
    }
}