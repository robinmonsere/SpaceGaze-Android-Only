package com.example.spacegaze.util

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacegaze.R
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

@VisibleForTesting
fun getTimeDifference(targetTime: String): Triple<String, String, String> {
    val now = Instant.now()
    val launchTime = Instant.parse(targetTime)
    val duration = Duration.between(now, launchTime)
    val hours = duration.toHours()
    val minutes = duration.minusHours(hours).toMinutes()
    val seconds = duration.minusHours(hours).minusMinutes(minutes).seconds
    return Triple(hours.toString(), minutes.toString(), seconds.toString())
}

// 2023-05-16T09:56:30.174Z
@VisibleForTesting
fun getTimeCleaned(dateTimeString: String): Pair<String, String> {
    val dateTime = Instant.parse(dateTimeString).atZone(ZoneId.systemDefault())

    val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    val timeString = dateTime.format(timeFormatter)

    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM")
    val dateString = dateTime.format(dateFormatter)

    return Pair(timeString, dateString)
}

@VisibleForTesting
fun getNotificationMinutes(dateTimeString: String): Long {
    val now = Instant.now()
    val launchTime = Instant.parse(dateTimeString)
    val duration = Duration.between(now, launchTime)
    return duration.toMinutes() - 10
}

@Composable
fun getAsyncImage(
    url: String,
    shape: RoundedCornerShape,
    desc: Int,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(desc),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .clip(shape = shape)
    )
}