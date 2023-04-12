package com.example.spacegaze.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Expand
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.spacegaze.R
import com.example.spacegaze.model.Launch
import com.example.spacegaze.model.Rocket
import com.example.spacegaze.model.RocketConfiguration
import com.example.spacegaze.ui.theme.ExtendedTheme
import com.example.spacegaze.ui.theme.SpaceGazeTheme
import com.example.spacegaze.util.getTimeCleaned

@Composable
fun LaunchScreen(
    launch: Launch,
    onReturn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(end = 20.dp)
    ) {
        Title(launch.rocket.configuration?.name,onReturn)
        ImageAndInfo(launch)
    }

}

@Composable
fun ImageAndInfo(
    launch: Launch,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val(time, date) = getTimeCleaned(launch.net)
    Row() {
        launch.mission?.name?.let {
            Text(it,
                modifier
                    .padding(top = 10.dp)
                    .clickable { expanded = !expanded },
                style = MaterialTheme.typography.h2) }
        IconToggleButton(
            checked = expanded,
            onCheckedChange = { checked -> expanded = checked },

        ) {
            if (expanded) {
                Icon(
                    imageVector = Icons.Rounded.ExpandLess,
                    contentDescription = stringResource(R.string.expand_less),
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.ExpandMore,
                    contentDescription = stringResource(R.string.expand_more)
                )
            }
        }
    }
    if (expanded) {
        launch.mission?.description?.let { Text(it, modifier.padding(bottom = 10.dp)) }
    }
     Box(
         modifier
             .padding(bottom = 10.dp)
             .fillMaxWidth()
             .height(200.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(launch.image)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.launcher_photo),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(10.dp)),
        )
    }
    Row(
        modifier
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
            .fillMaxWidth(),
        Arrangement.SpaceEvenly
    ) {
        InfoBlock(R.string.time, time)
        InfoBlock(R.string.date, date)
        launch.status?.let { InfoBlock(R.string.status, it.abbrev) }
    }
}

@Composable
fun InfoBlock(
    type: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(type), style = MaterialTheme.typography.body1, color = ExtendedTheme.colors.secondaryOnSurface)
        Text(value, style = MaterialTheme.typography.body2)
    }
}

@Composable
fun Title(
    launchName: String?,
    onReturn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        IconButton(
            onClick = { onReturn() },
            modifier
                .padding(end = 0.dp)
                .align(Alignment.TopStart)
                .background(MaterialTheme.colors.surface, RoundedCornerShape(10.dp))
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIos,
                contentDescription = stringResource(R.string.back_button)
            )
        }
        if (launchName != null) {
            Text(
                text = launchName,
                modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .wrapContentWidth(),
                style = MaterialTheme.typography.h1
            )
        }
    }

}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceGazeTheme() {
        LaunchScreen(
            launch = Launch("123", "Example Launch", rocket = Rocket(RocketConfiguration("rocket name", "rocket description", null)), status = null, lsp = null, net = "2023-03-30T10:00:00Z", mission = null, isUpcoming = false, image = null ),
            onReturn = {}
        )
    }
}

 */
