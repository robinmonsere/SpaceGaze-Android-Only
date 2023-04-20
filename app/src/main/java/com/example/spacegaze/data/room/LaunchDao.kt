package com.example.spacegaze.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.spacegaze.model.Launch
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {
    @Query(
        """
            SELECT * FROM launches
            where id = :id
        """
    )
    fun getLaunchById(id: String) : Flow<List<Launch>>

    @Query(
        """
            SELECT * FROM launches
            where isUpcoming = 1
        """
    )
    fun getUpcomingLaunches() : Flow<List<Launch>>

    @Query(
        """
            DELETE FROM launches
        """
    )
    fun clearLaunches()

    @Insert
    fun insertLaunch(launch: Launch)
}

