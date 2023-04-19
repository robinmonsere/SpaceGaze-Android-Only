package com.example.spacegaze.model

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpaceStationList(
    @SerialName("results")
    val launches: List<Launch>,
)

@Serializable
data class SpaceStation(
    val id: Int,
    val name: String,
    @Embedded("type_")
    val type: SpaceStationType,
    val founded: String,
    val description: String,
    val orbit: String,
    val owners: List<Owner>,
    @SerialName("image_url")
    val imageUrl: String
)

@Serializable
data class SpaceStationType(
    val id: Int,
    val name: String
)

@Serializable
data class Owner(
    val id: Int,
    val name: String
)