package com.example.spacegaze.ui.screens


import android.text.Layout.Alignment
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.spacegaze.R
import com.example.spacegaze.ui.theme.ExtendedTheme

private const val TAG = "SpaceStationScreen"

@Composable
fun SpaceStationScreen(
    modifier: Modifier = Modifier
) {
    var isActive by remember { mutableStateOf(true) }
    // header with Active | Inactive
    // start with viewmodel is active -> show active
    // on click set inactive
    Column(
        modifier = modifier.padding(end = 20.dp)
    ) {
        Text(
            stringResource(R.string.space_station),
            modifier = modifier.padding(bottom = 10.dp),
            style = MaterialTheme.typography.h1)
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.active),
                modifier = if (isActive) modifier
                    .weight(1f)
                    .bottomBorder(2.dp, ExtendedTheme.colors.accentColor)
                    .padding(bottom = 7.dp)
                    .clickable { isActive = !isActive }
                else modifier
                    .weight(1f)
                    .clickable { isActive = !isActive },
                textAlign =  TextAlign.Center,
                style = MaterialTheme.typography.h2
            )
            Text(
                stringResource(R.string.inactive),
                modifier = if (!isActive) modifier
                    .weight(1f)
                    .bottomBorder(2.dp, ExtendedTheme.colors.accentColor)
                    .padding(bottom = 7.dp)
                    .clickable { isActive = !isActive }
                else modifier
                    .weight(1f)
                    .clickable { isActive = !isActive },
                textAlign =  TextAlign.Center,
                style = MaterialTheme.typography.h2
            )
        }
       // LazyColumn()
    }
    
}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx/2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)
