package com.example.spacegaze.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacegaze.R
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.LaunchList
import com.example.spacegaze.ui.SpaceGazeUiState
import com.example.spacegaze.ui.theme.ExtendedTheme
import com.example.spacegaze.ui.theme.SpaceGazeTheme
import com.example.spacegaze.util.getTimeDifference
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import com.example.spacegaze.util.getTimeCleaned


@Composable
fun HomeScreen(
    spaceGazeUiState: SpaceGazeUiState,
    onViewLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (spaceGazeUiState) {
            is SpaceGazeUiState.NextLaunch -> {
                NextLaunch(spaceGazeUiState.nextLaunch.launches[0], onViewLaunch)
                val removedFirst = spaceGazeUiState.nextLaunch.launches.drop(1)
                ScheduledLaunches(removedFirst, onViewLaunch)
                RecentLaunches(spaceGazeUiState.nextLaunch.launches)
            }
            else -> {}
        }
    }
}

@Composable
fun NextLaunch(
    launch: Launch,
    onViewLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (hour, minutes, seconds) = getTimeDifference(launch.net)
    Column {
        Text(launch.name, modifier.padding(bottom = 5.dp), style = MaterialTheme.typography.h1)
        Text(
            stringResource(R.string.view_launch),
            modifier
                .clickable(onClick = { onViewLaunch() })
                .padding(bottom = 5.dp),
            color = ExtendedTheme.colors.secondaryOnSurface,
            style = MaterialTheme.typography.h2
        )
        Row(
            modifier
                .padding(top = 5.dp)
                .width(250.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimeBlock(R.string.hours, hour)
            TimeBlock(R.string.minutes, minutes)
            TimeBlock(R.string.seconds, seconds)
        }
    }
}

@Composable
fun TimeBlock(
    @StringRes timeType: Int,
    time : String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 25.dp, vertical = 8.dp)
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.h3,
            )
        }
        Spacer(modifier.height(10.dp))
        Text(
            text = stringResource(id = timeType),
            color = ExtendedTheme.colors.secondaryOnSurface,
        )
    }
}

@Composable
fun ScheduledLaunches (
    LaunchList: List<Launch>,
    onViewLaunch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column() {
        Text(
            text = stringResource(R.string.scheduled),
            style = MaterialTheme.typography.h1,
        )
        LazyRow(
            modifier.background(MaterialTheme.colors.background)
        ) {
            items(items = LaunchList) { launch ->
                CardItem(launch)
            }
        }
    }
}

@Composable
fun CardItem(
    launch: Launch,
    modifier: Modifier = Modifier
) {
    val(time, date) = getTimeCleaned(launch.net)
    Box(
        modifier
            .padding(end = 10.dp)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .width(300.dp)
            .height(150.dp),
    ) {
        Row(
            modifier.padding(10.dp)
        ) {
            Column() {
                launch.mission?.let { Text(text = it) }
                launch.lspName?.let { Text(text = it, color = ExtendedTheme.colors.secondaryOnSurface) }
                Spacer(
                    modifier.height(50.dp)
                )
                Row(
                    modifier
                        .height(IntrinsicSize.Min)
                ) {
                    Box(
                        modifier
                            .padding(end = 10.dp, top = 7.dp, bottom = 7.dp)
                            .background(
                                color = ExtendedTheme.colors.accentColor,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .width(2.dp)
                            .fillMaxHeight(),
                    )
                    Column() {
                        Text(time)
                        Text(date)
                    }
                }
            }
        }

    }
}

@Composable
fun RecentLaunches(
    LaunchList: List<Launch>,
    modifier: Modifier = Modifier,
) {
    Column() {
        Text(
            text = stringResource(R.string.recent),
            style = MaterialTheme.typography.h1,
        )
        LazyRow(
            modifier.background(MaterialTheme.colors.background)
        ) {
            items(items = LaunchList) { launch ->
                CardItem(launch)
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceGazeTheme() {
        HomeScreen(
            onViewLaunch = {},
            spaceGazeUiState =
        )
    }
}


 */
