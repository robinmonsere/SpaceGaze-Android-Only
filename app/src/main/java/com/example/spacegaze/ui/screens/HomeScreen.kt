package com.example.spacegaze.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spacegaze.R
import com.example.spacegaze.model.Launch
import com.example.spacegaze.ui.PreviousLaunchesUiState
import com.example.spacegaze.ui.SpaceGazeUiState
import com.example.spacegaze.ui.SpaceGazeViewModel
import com.example.spacegaze.util.getTimeCleaned
import com.example.spacegaze.util.getTimeDifference
import kotlinx.coroutines.delay

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    spaceGazeUiState: SpaceGazeUiState,
    viewModel: SpaceGazeViewModel,
    previousLaunchesUiState: PreviousLaunchesUiState,
    onViewUpcomingLaunch: (String) -> Unit,
    onViewRecentLaunch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(bottom = 100.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        when (spaceGazeUiState) {
            is SpaceGazeUiState.UpcomingLaunches -> {
                val launches = spaceGazeUiState.launchList
                if (launches.isNotEmpty()) {
                    NextLaunch(launches[0], onViewUpcomingLaunch)
                    ScheduledLaunches(launches.drop(1), onViewUpcomingLaunch, viewModel)
                }
            }
            is SpaceGazeUiState.Loading -> {
                LoadingImg()
            }
            else -> {
                Text(text = "Something went wrong")
            }
        }
        /*
        when (previousLaunchesUiState) {
            is PreviousLaunchesUiState.PreviousLaunches -> {
                val launches = previousLaunchesUiState.launchList
                RecentLaunches(launches.launches, onViewRecentLaunch)
            }
            is PreviousLaunchesUiState.Loading -> {
                LoadingImg()
            } is PreviousLaunchesUiState.Error -> {
                BrokenImg()
            }
            else -> {

            }
        }

         */
    }
}

@Composable
fun NextLaunch(
    launch: Launch,
    onViewLaunch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var remainingTime by remember { mutableStateOf(getTimeDifference(launch.net)) }

    LaunchedEffect(Unit) {
        while (true) {
            remainingTime = getTimeDifference(launch.net)
            delay(1000)
        }
    }

    Column {
        launch.mission?.name?.let { Text(it, modifier.padding(bottom = 5.dp), style = MaterialTheme.typography.h1) }
        Text(
            stringResource(R.string.view_launch),
            modifier
                .clickable() {
                    onViewLaunch(launch.id)
                }
                .padding(bottom = 5.dp),
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.h2
        )
        Row(
            modifier
                .padding(top = 5.dp)
                .width(250.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimeBlock(R.string.hours, remainingTime.first)
            TimeBlock(R.string.minutes, remainingTime.second)
            TimeBlock(R.string.seconds, remainingTime.third)
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
                color = MaterialTheme.colors.onBackground,
            )
        }
        Spacer(modifier.height(10.dp))
        Text(
            text = stringResource(id = timeType),
            color = MaterialTheme.colors.secondary,
        )
    }
}

@Composable
fun ScheduledLaunches (
    LaunchList: List<Launch>,
    onViewLaunch: (String) -> Unit,
    viewModel: SpaceGazeViewModel,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = stringResource(R.string.scheduled),
            style = MaterialTheme.typography.h1,
        )
        val listState = rememberLazyListState()
        LazyRow(
            modifier.background(MaterialTheme.colors.background),
            state = listState
        ) {
            items(items = LaunchList) { launch ->
                LaunchCardItem(launch, onViewLaunch)
            }
            if (listState.firstVisibleItemIndex >= viewModel.loaded - 3) {
                viewModel.loadMoreUpcomingLaunches()
            }
            item {
                loadMoreLaunchesCardItem()
            }
        }
        Text(text = listState.firstVisibleItemIndex.toString())

    }
}

@Composable
private fun loadMoreLaunchesCardItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .padding(end = 10.dp)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .width(300.dp)
            .height(150.dp)
            .padding(10.dp)
    ) {

    }
}

@Composable
private fun LaunchCardItem(
    launch: Launch,
    onViewLaunch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val(time, date) = getTimeCleaned(launch.net)
    Box(
        modifier
            .padding(end = 10.dp)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .width(300.dp)
            .height(150.dp)
            .padding(10.dp)
            .clickable { onViewLaunch(launch.id) },
    ) {
            Column(
                modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    launch.mission?.name?.let { Text(text = it) }
                    launch.lsp?.name?.let { Text(text = it, color = MaterialTheme.colors.secondary) }
                }
                Row(
                    modifier
                        .height(IntrinsicSize.Min)
                ) {
                    Box(
                        modifier
                            .padding(end = 10.dp, top = 7.dp, bottom = 7.dp)
                            .background(
                                color = MaterialTheme.colors.secondaryVariant,
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

@Composable
fun RecentLaunches(
    LaunchList: List<Launch>,
    onViewLaunch: (String) -> Unit,
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
                LaunchCardItem(launch, onViewLaunch)
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
