package com.example.spacegaze.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacegaze.R
import com.example.spacegaze.model.SpaceStation
import com.example.spacegaze.ui.SpaceStationUiState
import com.example.spacegaze.ui.theme.ExtendedTheme

private const val TAG = "SpaceStationScreen"

@Composable
fun SpaceStationOverviewScreen(
    spaceStationUiState: SpaceStationUiState,
    onSpaceStation: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(end = 20.dp, bottom = 50.dp)
    ) {
        var isActive by remember { mutableStateOf(true) }
        Text(
            stringResource(R.string.space_station),
            modifier = modifier.padding(bottom = 10.dp),
            style = MaterialTheme.typography.h1
        )
        ActiveToggle(isActive, { isActive = true }, { isActive = false })
        when (spaceStationUiState) {
            is SpaceStationUiState.SpaceStations -> {
                val spaceStations = if (isActive) spaceStationUiState.ActiveSpaceStationList else spaceStationUiState.InActiveSpaceStationList
                SpaceStationsList(spaceStations, onSpaceStation)
            }
            is SpaceStationUiState.Loading -> {
                Image(
                    painterResource(R.drawable.loading_img),
                    stringResource(R.string.loading)
                )
            }
        }
    }
}

@Composable
fun ActiveToggle(
    isActive: Boolean,
    onActive: () -> Unit,
    onInActive: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Text(
            stringResource(R.string.active),
            modifier = if (isActive) modifier
                .weight(1f)
                .bottomBorder(2.dp, ExtendedTheme.colors.accentColor)
                .padding(bottom = 7.dp)
                .clickable { onActive() }
            else modifier
                .weight(1f)
                .clickable { onActive() },
            textAlign =  TextAlign.Center,
            style = MaterialTheme.typography.h2
        )
        Text(
            stringResource(R.string.inactive),
            modifier = if (!isActive) modifier
                .weight(1f)
                .bottomBorder(2.dp, ExtendedTheme.colors.accentColor)
                .padding(bottom = 7.dp)
                .clickable { onInActive() }
            else modifier
                .weight(1f)
                .clickable { onInActive() },
            textAlign =  TextAlign.Center,
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
fun SpaceStationsList(
    spaceStations: List<SpaceStation>,
    onViewSpaceStation: (Int) -> Unit
) {
    LazyColumn() {
        items(items = spaceStations) { station ->
            SpaceStationCardItem(station, onViewSpaceStation)
        }
    }
}

@Composable
fun SpaceStationCardItem(
    station: SpaceStation,
    onViewSpaceStation: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .clickable { onViewSpaceStation(station.id) }

    ) {
        Column() {
            Row(
                modifier
                    .padding(15.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Box(
                    modifier
                        .padding(end = 5.dp)
                        .background(
                            color = ExtendedTheme.colors.accentColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .width(2.dp)
                        .fillMaxHeight(),
                )
                Text(
                    station.name,
                    style = MaterialTheme.typography.h2,
                )
            }
            Box(
                modifier
                    .fillMaxSize()
                    .height(200.dp)

            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(station.imageUrl)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = stringResource(R.string.station_photo),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)),
                )
            }
        }
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
