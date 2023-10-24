package com.example.spacegaze

import com.example.spacegaze.util.getNotificationMinutes
import com.example.spacegaze.util.getTimeCleaned
import com.example.spacegaze.util.getTimeDifference
import org.junit.Test
import org.junit.Assert.*
import org.threeten.bp.Instant

internal class SpaceGazeUnitTest {
    @Test
    fun calculateNotificationTime() {
        val notificationMinutes = getNotificationMinutes(Instant.now().toString())
        assertEquals(-10, notificationMinutes)
    }

    @Test
    fun cleanTime() {
        val timeClean = getTimeDifference(Instant.now().toString())
        assertEquals(timeClean, Triple(0,0,-1))
    }
}