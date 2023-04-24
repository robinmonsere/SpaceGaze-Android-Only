package com.example.spacegaze.ui.screens

import android.media.audiofx.AudioEffect.Descriptor
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
       modifier = modifier.padding(end = 10.dp)
   ) {
       Title(spaceStation.name, { onReturn() })
       SpaceStationImage(spaceStation)
       Description(spaceStation)


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