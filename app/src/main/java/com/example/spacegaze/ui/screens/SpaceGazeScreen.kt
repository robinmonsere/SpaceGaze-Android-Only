package com.example.spacegaze.ui.screens

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spacegaze.R
import com.example.spacegaze.ui.SpaceGazeViewModel

enum class SpaceGazeScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    Launch(title = R.string.view_launch)
}

/*
@Composable
fun SpaceGazeTopBar(
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    @StringRes currentScreenTitle: Int,
) {
    TopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}
*/

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
                    imageVector = Icons.Rounded.RocketLaunch,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SpaceGazeApp(
    modifier: Modifier = Modifier,
) {
    val spaceGazeViewModel: SpaceGazeViewModel = viewModel(factory = SpaceGazeViewModel.Factory)
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            SpaceGazeBottomBar(
                onHome = {
                    navController.popBackStack(SpaceGazeScreen.Home.name, inclusive = false)
                },
                onLaunch = {
                    navController.navigate(SpaceGazeScreen.Launch.name)
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
                val launch = launchId
                LaunchScreen(
                    launch,
                    onReturn = { navController.popBackStack(SpaceGazeScreen.Home.name, inclusive = false) }
                )
            }
        }
    }
}