package com.example.spacegaze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.spacegaze.notifications.launchNotificationManager
import com.example.spacegaze.ui.screens.SpaceGazeApp
import com.example.spacegaze.ui.theme.SpaceGazeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceGazeTheme {
                SpaceGazeApp()
                launchNotificationManager(applicationContext)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceGazeTheme {
        SpaceGazeApp()
    }
}