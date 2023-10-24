package com.example.spacegaze.ui.screens

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SatelliteAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SatelliteAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spacegaze.R
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.SpaceStation
import com.example.spacegaze.ui.PreviousLaunchesViewModel
import com.example.spacegaze.ui.SpaceGazeViewModel
import com.example.spacegaze.ui.SpaceStationViewModel
import org.threeten.bp.ZonedDateTime
import java.util.*
import androidx.lifecycle.viewModelScope
import com.example.spacegaze.ui.PreviousLaunchesUiState

enum class SpaceGazeScreen {
    Home,
    UpcomingLaunch,
    PreviousLaunch,
    SpaceStation,
    SpaceStationOverview,
    Settings,

}

private const val TAG = "SpaceGazeScreen"

@Composable
fun SpaceGazeBottomBar(
    onHome: () -> Unit,
    onSpaceStationOverview: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Row(
            modifier.fillMaxWidth(),
            Arrangement.SpaceAround
        ) {
            var isSettingsSelected by remember { mutableStateOf(false) }
            var isSpaceStationSelected by remember { mutableStateOf(false) }
            var isHomeSelected by remember { mutableStateOf(true) }

            ToggleIconButtonCustom(
                selectedIcon = Icons.Rounded.Home,
                unselectedIcon = Icons.Outlined.Home,
                isSelected = isHomeSelected,
                onClick = {
                    isHomeSelected = true
                    isSpaceStationSelected = false
                    isSettingsSelected = false
                    onHome()
                },
                contentDescription = R.string.home
            )
            ToggleIconButtonCustom(
                selectedIcon = Icons.Filled.SatelliteAlt,
                unselectedIcon = Icons.Outlined.SatelliteAlt,
                isSelected = isSpaceStationSelected,
                onClick = {
                    isSpaceStationSelected = true
                    isHomeSelected = false
                    isSettingsSelected = false
                    onSpaceStationOverview()
                },
                contentDescription = R.string.space_station
            )
            ToggleIconButtonCustom(
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                isSelected = isSettingsSelected,
                onClick = {
                    isSpaceStationSelected = false
                    isHomeSelected = false
                    isSettingsSelected = true
                    onSettings()
                },
                contentDescription = R.string.settings
            )
        }
    }
}

@Composable
fun ToggleIconButtonCustom(
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    isSelected: Boolean,
    contentDescription: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = stringResource(contentDescription),
            modifier = Modifier.size(30.dp),
        )
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SpaceGazeApp(
    modifier: Modifier = Modifier,
    spaceGazeViewModel: SpaceGazeViewModel = viewModel(factory = SpaceGazeViewModel.Factory),
    previousLaunchesViewModel: PreviousLaunchesViewModel = viewModel(factory = PreviousLaunchesViewModel.Factory),
    spaceStationViewModel: SpaceStationViewModel = viewModel(factory = SpaceStationViewModel.Factory)
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            SpaceGazeBottomBar(
                onHome = {
                    navController.popBackStack(SpaceGazeScreen.Home.name, inclusive = false)
                },
                onSpaceStationOverview = {
                    navController.navigate(SpaceGazeScreen.SpaceStationOverview.name)
                },
                onSettings = {
                    navController.navigate(SpaceGazeScreen.Settings.name)
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = SpaceGazeScreen.Home.name,
            modifier = modifier.padding(start = 20.dp, top= 10.dp),
        ) {
            composable(route = SpaceGazeScreen.Home.name) {
                HomeScreen(
                    spaceGazeUiState = spaceGazeViewModel.spaceGazeUiState,
                    previousLaunchesUiState = previousLaunchesViewModel.previousLaunchesUiState,
                    viewModel = spaceGazeViewModel,
                    onViewUpcomingLaunch = { launchId ->
                        navController.navigate("${SpaceGazeScreen.UpcomingLaunch.name}/$launchId") },
                    onViewRecentLaunch = { launchId ->
                        navController.navigate("${SpaceGazeScreen.PreviousLaunch.name}/$launchId") },
                )
            }
            val launchIdArgument = "launchId"
            composable(
                route = SpaceGazeScreen.UpcomingLaunch.name + "/{$launchIdArgument}",
                arguments = listOf(navArgument(launchIdArgument) {type = NavType.StringType})
            ) { backStackEntry ->
                val launchId = backStackEntry.arguments?.getString(launchIdArgument)
                    ?: error("launchIdArgument can not be null")
                val context = LocalContext.current
                val launch by spaceGazeViewModel.getLaunchById(launchId).collectAsState(emptyList())
                if (launch.isNotEmpty()) {
                    LaunchScreen(
                        launch[0],
                        onReturn = { navController.popBackStack(SpaceGazeScreen.Home.name, inclusive = false) },
                        onOpenMaps = { location: String -> openMaps(context, location) },
                        onAddToCalendar = { launch: Launch -> addToCalendar(context, launch) }
                    )
                }
            }
            composable(
                route = SpaceGazeScreen.PreviousLaunch.name + "/{$launchIdArgument}",
                arguments = listOf(navArgument(launchIdArgument) {type = NavType.StringType})
            ) {backStackEntry ->
                val launchId = backStackEntry.arguments?.getString(launchIdArgument)
                    ?: error("launchIdArgument can not be null")
                val context = LocalContext.current
                previousLaunchesViewModel.getLaunchById(launchId)
                when (val uiState = previousLaunchesViewModel.previousLaunchesUiState) {
                    is PreviousLaunchesUiState.PreviousLaunch -> {
                        LaunchScreen(
                            uiState.launch,
                            onReturn = { navController.popBackStack(SpaceGazeScreen.Home.name, inclusive = false) },
                            onOpenMaps = { location: String -> openMaps(context, location) },
                            onAddToCalendar = { launch: Launch -> addToCalendar(context, launch) }
                        )
                    }
                    is PreviousLaunchesUiState.Loading -> {
                        Log.e(TAG, "Tets")
                        LoadingImg()
                    } else -> {
                        BrokenImg()
                    }
                }
            }
            composable(
                route = SpaceGazeScreen.SpaceStationOverview.name,
            ) {
                SpaceStationOverviewScreen(
                    spaceStationUiState = spaceStationViewModel.spaceStationUiState,
                    onSpaceStation = { stationId -> navController.navigate("${SpaceGazeScreen.SpaceStation.name}/$stationId") }
                )
            }
            val spaceStationArgument = "stationId"
            composable(
                route = SpaceGazeScreen.SpaceStation.name+ "/{$spaceStationArgument}",
                arguments = listOf(navArgument(spaceStationArgument) {type = NavType.StringType})
            ) {backStackEntry ->
                val stationId = backStackEntry.arguments?.getString(spaceStationArgument)
                    ?: error("spaceStationArgument can not be null")
                val spaceStation by spaceStationViewModel.getStationById(stationId.toInt()).collectAsState(null)
                spaceStation?.let { station -> SpaceStationScreen(
                    station,
                    onReturn = { navController.popBackStack(SpaceGazeScreen.SpaceStationOverview.name, inclusive = false) }) }
            }
            composable(
                route = SpaceGazeScreen.Settings.name,
            ) {
                SettingsScreen()
            }
        }
    }
}

private fun openMaps(context: Context, location: String) {
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(location))
    try {
        context.startActivity(mapIntent)
    } catch (e: ActivityNotFoundException) {
        Log.e(TAG, e.toString())
    }
}



private fun addToCalendar (context: Context, launch: Launch) {
    val startTime = ZonedDateTime.parse(launch.net)
    val calendarIntent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.Events.TITLE, launch.mission?.name)
        .putExtra(CalendarContract.Events.DESCRIPTION, launch.mission?.description)
        .putExtra(CalendarContract.Events.EVENT_LOCATION, launch.pad?.name)
        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime.toEpochSecond() * 1000)
        .putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)

    try {
        context.startActivity(calendarIntent)
    } catch (e: ActivityNotFoundException) {
        Log.e(TAG, e.toString())
    }
}