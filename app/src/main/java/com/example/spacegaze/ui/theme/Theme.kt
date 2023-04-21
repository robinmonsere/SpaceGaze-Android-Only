package com.example.spacegaze.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorPalette = darkColors(
    background = Black,
    onBackground = White,
    surface = SurfaceGray,
    onSurface = White,
    secondary = onSurfaceGray,
    //onSecondary = AccentRed,
)

val LightColorPalette = lightColors(
    // take a look at the dark colors and change the colors in lightColorPallet
    background = White,
    onBackground = Black,
    surface = onSurfaceGray,
    onSurface = Black,
    secondary = Black,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SpaceGazeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}