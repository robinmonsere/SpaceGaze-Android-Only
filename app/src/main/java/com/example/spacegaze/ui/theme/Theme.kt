package com.example.spacegaze.ui.theme

import android.annotation.SuppressLint
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
    primaryVariant = BottomBarGray,
    onPrimary = IconGray,
    secondaryVariant = AccentRed,
)

@SuppressLint("ConflictingOnColor")
val LightColorPalette = lightColors(
    background = BackgroundWhite,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    secondary = Purple,
    primaryVariant = White,
    onPrimary = Black,
    secondaryVariant = AccentRed,
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