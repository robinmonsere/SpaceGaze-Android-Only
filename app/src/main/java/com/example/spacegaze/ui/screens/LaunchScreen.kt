package com.example.spacegaze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Bathtub
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spacegaze.R
import com.example.spacegaze.ui.theme.SpaceGazeTheme

@Composable
fun LaunchScreen(
    launchId: String,
    onReturn: () -> Unit
) {

    Title(launchId,onReturn)
}

@Composable
fun Title(
    launchName: String,
    onReturn: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onReturn() },
        modifier
            .padding(end = 0.dp)
            .background(MaterialTheme.colors.surface, RoundedCornerShape(10.dp))
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIos,
            contentDescription = stringResource(R.string.back_button)
        )
    }
    Text(
        text = launchName,
        modifier.fillMaxWidth().wrapContentWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1
    )
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpaceGazeTheme() {
        LaunchScreen(

        )
    }
}
*/