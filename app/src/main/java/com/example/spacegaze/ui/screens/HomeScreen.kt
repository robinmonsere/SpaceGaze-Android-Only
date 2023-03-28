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
import com.example.spacegaze.ui.SpaceGazeUiState
import com.example.spacegaze.ui.theme.ExtendedTheme
import com.example.spacegaze.ui.theme.SpaceGazeTheme
import com.example.spacegaze.util.getTimeDifference


@Composable
fun HomeScreen(
    spaceGazeUiState: SpaceGazeUiState,
    onViewLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(start = 20.dp, bottom = 100.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (spaceGazeUiState) {
            is SpaceGazeUiState.NextLaunch -> NextLaunch(spaceGazeUiState.nextLaunch.launches[0], onViewLaunch)
            else -> {}
        }
        //NextLaunch()
        ScheduledLaunches()
        RecentLaunches()
    }
}

@Composable
fun NextLaunch(
    launch: Launch,
    onViewLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (hour, minutes, seconds) = getTimeDifference(launch.net)
    Column() {
        Text(launch.name, style = MaterialTheme.typography.h1)
        Text(
            stringResource(R.string.view_launch),
            modifier.clickable(onClick = { onViewLaunch() }),
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
            items(20) {
                CardItem(launch = it)
            }
        }
    }
}

@Composable
fun CardItem(
    launch: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .padding(end = 10.dp)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .fillMaxWidth(),
    ) {
        Row(
            modifier.padding(10.dp)
        ) {
            Column(
                //verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Test")
                Text(text = "Crew-6")
                Divider(
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
                        Text(text = "Test")
                        Text(text = "Crew-6")
                    }
                }

            }
            Box(
                modifier
                    .width(200.dp)
                    .background(Color.White)
            )
        }

    }
}

@Composable
fun RecentLaunches(
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
            items(20) {
                CardItem(launch = it)
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
