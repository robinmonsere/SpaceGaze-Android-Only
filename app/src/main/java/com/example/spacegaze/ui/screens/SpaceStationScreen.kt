package com.example.spacegaze.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.spacegaze.model.SpaceStation

@Composable 
fun SpaceStationScreen(
    spaceStation: SpaceStation,

) {
    Text(text = spaceStation.name)
}