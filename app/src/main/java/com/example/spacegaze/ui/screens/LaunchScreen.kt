package com.example.spacegaze.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    onReturn: () -> Unit
) {
    Column() {
        Title(launch.rocket.configuration?.name,onReturn)
        ImageAndInfo(launch)
    }

}

@Composable
fun ImageAndInfo(
    launch: Launch,
    modifier: Modifier = Modifier
) {
    val(time, date) = getTimeCleaned(launch.net)
    Box() {

    }
    Row(
        modifier
            .padding(end = 20.dp)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(5.dp))
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceGazeTheme() {
        LaunchScreen(
            launch = Launch("123", "Example Launch", rocket = Rocket(RocketConfiguration("rocket name", "rocket description", null)), status = null, lsp = null, net = "2023-03-30T10:00:00Z", mission = null, isUpcoming = false ),
            onReturn = {}
        )
    }
}
