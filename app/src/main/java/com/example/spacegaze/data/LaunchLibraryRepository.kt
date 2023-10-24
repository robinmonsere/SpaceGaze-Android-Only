package com.example.spacegaze.data

import androidx.compose.ui.geometry.Offset
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.model.SpaceStationList
import com.example.spacegaze.network.LaunchLibraryApiService

interface LaunchLibraryRepository {
    suspend fun getUpcomingLaunches(limit: Int, offset: Int): LaunchList

    suspend fun getPreviousLaunches(): LaunchList
    suspend fun getPreviousLaunchById(string: String): Launch
    suspend fun getSpaceStations(): SpaceStationList
}

class NetworkLaunchLibraryRepository(
    private val LaunchLibraryApiService: LaunchLibraryApiService
) : LaunchLibraryRepository {
    override suspend fun getUpcomingLaunches(limit: Int, offset: Int): LaunchList = LaunchLibraryApiService.getUpcomingLaunches(limit, offset)

    override suspend fun getPreviousLaunchById(id: String): Launch = LaunchLibraryApiService.getLaunchById(id)

    override suspend fun getPreviousLaunches(): LaunchList = LaunchLibraryApiService.getPreviousLaunches()

    override suspend fun getSpaceStations(): SpaceStationList = LaunchLibraryApiService.getSpaceStations()
}