package com.example.spacegaze

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spacegaze.ui.HomeScreen

enum class SpaceGazeScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
}

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

@Composable
fun SpaceGazeBottomBar() {
    BottomAppBar() {
        Row() {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SpaceGazeApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = SpaceGazeScreen.valueOf(
        backStackEntry?.destination?.route ?: SpaceGazeScreen.Home.name
    )
    Scaffold(
        topBar = {
            SpaceGazeTopBar(
                canNavigateBack = false,
                navigateUp = { /*TODO*/ },
                currentScreenTitle = currentScreen.title,
            )
        },
        bottomBar = {
            SpaceGazeBottomBar()
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = SpaceGazeScreen.Home.name,
            modifier = modifier,
        ) {
            composable(route = SpaceGazeScreen.Home.name) {
                HomeScreen()
            }
        }


    }
}