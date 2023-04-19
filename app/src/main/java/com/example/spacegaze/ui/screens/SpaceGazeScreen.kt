package com.example.spacegaze.ui.screens

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.SatelliteAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spacegaze.R
import com.example.spacegaze.model.Launch
import com.example.spacegaze.ui.SpaceGazeViewModel
import com.example.spacegaze.ui.theme.AccentRed
import org.threeten.bp.ZonedDateTime
import java.sql.Timestamp
import java.util.*

enum class SpaceGazeScreen {
    Home,
    Launch,
    SpaceStation

}
private const val TAG = "SpaceGazeScreen"


@Composable
fun SpaceGazeBottomBar(
    onHome: () -> Unit,
    onLaunch: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
    ) {
        Row() {
            IconButton(
                onClick = onHome
            ) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
            IconButton(onClick = onLaunch ) {
                Icon(
                    imageVector = Icons.Rounded.SatelliteAlt,
                    contentDescription = stringResource(R.string.space_station)
                )
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SpaceGazeApp(
    modifier: Modifier = Modifier,
    spaceGazeViewModel: SpaceGazeViewModel = viewModel(factory = SpaceGazeViewModel.Factory)
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            SpaceGazeBottomBar(
                onHome = {
                    navController.popBackStack(SpaceGazeScreen.Home.name, inclusive = false)
                },
                onLaunch = {
                    navController.navigate(SpaceGazeScreen.SpaceStation.name)
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = SpaceGazeScreen.Home.name,
            modifier = modifier.padding(start = 20.dp, bottom = 100.dp, top= 20.dp),
        ) {
            composable(route = SpaceGazeScreen.Home.name) {
                HomeScreen(
                    spaceGazeUiState = spaceGazeViewModel.spaceGazeUiState,
                    viewModel = spaceGazeViewModel,
                    onViewLaunch = { launchId ->
                        navController.navigate("${SpaceGazeScreen.Launch.name}/$launchId") }
                )
            }
            val launchIdArgument = "launchId"
            composable(
                route = SpaceGazeScreen.Launch.name + "/{$launchIdArgument}",
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
                        onOpenMaps = { location: String ->
                            openMaps(context, location) },
                        onAddToCalendar = { launch: Launch ->
                            addToCalendar(context, launch) }
                    )
                }
            }
            composable(
                route = SpaceGazeScreen.SpaceStation.name,
            ) {
                SpaceStationScreen()
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