package com.example.spacegaze.util

import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


fun getTimeDifference(targetTime: String): Triple<String, String, String> {
    val now = Instant.now()
    val launchTime = Instant.parse(targetTime)
    val duration = Duration.between(now, launchTime)
    val hours = duration.toHours()
    val minutes = duration.minusHours(hours).toMinutes()
    val seconds = duration.minusHours(hours).minusMinutes(minutes).seconds
    return Triple(hours.toString(), minutes.toString(), seconds.toString())
}

fun getTimeCleaned(dateTimeString: String): Pair<String, String> {
    val dateTime = Instant.parse(dateTimeString).atZone(ZoneId.systemDefault())

    val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    val timeString = dateTime.format(timeFormatter)

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val dateString = dateTime.format(dateFormatter)

    return Pair(timeString, dateString)
}