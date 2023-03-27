package com.example.spacegaze.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors


@Immutable
data class ExtendedColors(
    val accentColor: Color,
    val secondaryOnSurface: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        accentColor = AccentRed,
        secondaryOnSurface = onSurfaceGray
    )
}

@Composable
fun ExtendedTheme(
    /* ... */
    content: @Composable () -> Unit
) {
    val extendedColors = ExtendedColors(
        accentColor = AccentRed,
        secondaryOnSurface = onSurfaceGray
    )
    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            /* colors = ..., typography = ..., shapes = ... */
            content = content
        )
    }
}

// Use with eg. ExtendedTheme.colors.tertiary
object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}