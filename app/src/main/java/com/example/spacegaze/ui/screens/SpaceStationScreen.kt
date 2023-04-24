package com.example.spacegaze.ui.screens

import android.media.audiofx.AudioEffect.Descriptor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.spacegaze.R
import com.example.spacegaze.model.SpaceStation
import com.example.spacegaze.util.getAsyncImage
import kotlin.math.exp

@Composable 
fun SpaceStationScreen(
    spaceStation: SpaceStation,
    onReturn: () -> Unit,
    modifier: Modifier = Modifier
) {
   Column(
       modifier = modifier.padding(end = 20.dp, bottom = 60.dp)
   ) {
       Title(spaceStation.name, { onReturn() })
       LazyColumn() {
           items(count = 1) {
               SpaceStationImage(spaceStation)
               SpaceStationInformation(spaceStation)
               Description(spaceStation)
           }

       }

   }
}

@Composable
fun SpaceStationInformation(
    spaceStation: SpaceStation,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(bottom = 10.dp)
            .background(MaterialTheme.colors.surface, shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            InfoBlock(R.string.status,spaceStation.status.name, modifier.weight(1f/2f))
            InfoBlock(R.string.type, spaceStation.type.name, modifier.weight(1f/2f))
        }
        Row(
            modifier.fillMaxWidth()
        ) {
            InfoBlock(R.string.founded,spaceStation.founded, modifier.weight(1f/2f))
            InfoBlock(R.string.orbit, spaceStation.orbit, modifier.weight(1f/2f))
        }
    }
}

@Composable
fun Title(
    name: String?,
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
        if (name != null) {
            Text(
                text = name,
                modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .wrapContentWidth(),
                style = MaterialTheme.typography.h3,

            )
        }
    }
}

@Composable
fun Description(
    spaceStation: SpaceStation,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val description = if (expanded) spaceStation.description else spaceStation.description.take(100) + "..."
    Text(
        text = description,
        modifier = modifier
            .clickable { expanded = !expanded }
    )
}

@Composable
fun SpaceStationImage(
    spaceStation: SpaceStation,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(200.dp)
    ) {
        getAsyncImage(url = spaceStation.imageUrl, shape = RoundedCornerShape(10.dp), desc = R.string.space_station)
    }
}