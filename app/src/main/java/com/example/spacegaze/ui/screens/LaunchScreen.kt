package com.example.spacegaze.ui.screens


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
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
import com.example.spacegaze.data.preview.DataSource
import com.example.spacegaze.model.Launch
import com.example.spacegaze.ui.theme.SpaceGazeTheme
import com.example.spacegaze.util.getAsyncImage
import com.example.spacegaze.util.getTimeCleaned

@Composable
fun LaunchScreen(
    launch: Launch,
    onReturn: () -> Unit,
    onOpenMaps: (String) -> Unit,
    onAddToCalendar: (Launch) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(end = 20.dp)
    ) {
        LaunchTitle(launch.rocket.configuration?.name,onReturn)
        NameAndDescription(launch)
        Image(launch)
        Information(launch, onOpenMaps, onAddToCalendar)
    }
}


@Composable
fun NameAndDescription(
    launch: Launch,
    modifier: Modifier = Modifier
)
{
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    ) {
        Row {
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
    }
}

@Composable
fun Image(
    launch: Launch,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(200.dp)
    ) {
        launch.image?.let { getAsyncImage(url = it, shape = RoundedCornerShape(10.dp), desc = R.string.launcher_photo) }
    }
}

@Composable
fun Information(
    launch: Launch,
    onOpenMaps: (String) -> Unit,
    onAddToCalendar: (Launch) -> Unit,
    modifier: Modifier = Modifier
) {
    val(time, date) = getTimeCleaned(launch.net)
    Column(
        modifier
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
        ) {
            InfoBlock(R.string.time, time, modifier.weight(1f/3f))
            InfoBlock(R.string.date, date, modifier.weight(1f/3f))
            launch.status?.let { InfoBlock(R.string.status, it.abbrev,modifier.weight(1f/3f)) }
        }
        Row(
            modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .clickable { onAddToCalendar(launch) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.add_to_calendar))
            Icon(Icons.Rounded.EditCalendar, stringResource(R.string.add_to_calendar))
        }
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            InfoBlock(R.string.pad, launch.pad?.name)
        }
        Row(
            modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .clickable { launch.pad?.mapUrl?.let { onOpenMaps(it) } },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.view_map))
            Icon(Icons.Rounded.Map, stringResource(R.string.view_map))
        }
        Row(
            modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            InfoBlock(R.string.lsp, launch.lsp?.name)
        }
    }

}

@Composable
fun InfoBlock(
    type: Int,
    value: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(type), style = MaterialTheme.typography.body1, color = MaterialTheme.colors.secondary)
        Text(value ?: stringResource(R.string.unknown), style = MaterialTheme.typography.body2, color = MaterialTheme.colors.onBackground, textAlign = TextAlign.Center)
    }
}

@Composable
fun LaunchTitle(
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
    SpaceGazeTheme {
        LaunchTitle(
            launchName = DataSource().getLaunchPreviewData().rocket.configuration?.name,
            onReturn = {}
        )
        NameAndDescription(launch = DataSource().getLaunchPreviewData())
        Image(launch = DataSource().getLaunchPreviewData())
    }
}




