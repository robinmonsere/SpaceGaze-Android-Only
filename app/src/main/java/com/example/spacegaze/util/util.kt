package com.example.spacegaze.util

import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun getTimeDifference(targetTime: String): Triple<String, String, String> {
    val now = Instant.now()
    val launchTime = Instant.parse(targetTime)
    val duration = Duration.between(now, launchTime)
    val hours = duration.toHours()
    val minutes = duration.minusHours(hours).toMinutes()
    val seconds = duration.minusHours(hours).minusMinutes(minutes).seconds
    return Triple(hours.toString(), minutes.toString(), seconds.toString())
}