package com.example.spacegaze.data

import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.model.SpaceStationList
import com.example.spacegaze.network.LaunchLibraryApiService

interface LaunchLibraryRepository {
    suspend fun getUpcomingLaunches(): LaunchList

    suspend fun getPreviousLaunches(): LaunchList
    suspend fun getPreviousLaunchById(string: String): Launch
    suspend fun getSpaceStations(): SpaceStationList
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

    override suspend fun getPreviousLaunchById(id: String): Launch = LaunchLibraryApiService.getLaunchById(id)

    override suspend fun getPreviousLaunches(): LaunchList = LaunchLibraryApiService.getPreviousLaunches()

    override suspend fun getSpaceStations(): SpaceStationList = LaunchLibraryApiService.getSpaceStations()
}