package com.example.spacegaze.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.SpaceStation
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {
    @Query(
        """
            SELECT * FROM launches
            where id = :id
        """
    )
    fun getLaunchById(id: String): Flow<List<Launch>>

    @Query(
        """
            SELECT * FROM launches
            where isUpcoming = 1
        """
    )
    fun getUpcomingLaunches(): Flow<List<Launch>>

    @Query(
        """
            DELETE FROM launches
        """
    )
    fun clearLaunches()

    @Insert
    fun insertLaunch(launch: Launch)

    /* ------------------------------ */
    //        Space station           //
    /* ------------------------------ */

    @Insert
    fun insertSpaceStation(spaceStation: SpaceStation)

    @Query(
        """
            DELETE FROM space_stations
        """
    )
    fun clearStations()

    @Transaction
    @Query("SELECT * FROM space_stations")
    fun getStationsWithOwners(): List<SpaceStation>

   @Query(
        """
            SELECT * FROM space_stations
            where status_id = 1
        """
    )
    fun getActiveStations(): List<SpaceStation>

    @Query(
        """
            SELECT * FROM space_stations
            where status_id != 1
        """
    )
    fun getInActiveStations(): List<SpaceStation>

    @Query(
        """
            SELECT * FROM space_stations
            where id = :id
        """
    )
    fun getStationFromId(id: Int): Flow<SpaceStation>


}
