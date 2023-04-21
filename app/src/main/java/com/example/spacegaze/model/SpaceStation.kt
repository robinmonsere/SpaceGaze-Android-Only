package com.example.spacegaze.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpaceStationList(
    @SerialName("results")
    val spaceStations: List<SpaceStation>,
)

@Serializable
@Entity(tableName = "space_stations")
data class SpaceStation(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    @Embedded("status_")
    val status: SpaceStationStatus,
    @Embedded("type_")
    val type: SpaceStationType,
    val founded: String,
    val description: String,
    val orbit: String,
    @SerialName("image_url")
    val imageUrl: String
)

@Serializable
@Entity
data class SpaceStationStatus(
    val id: Int,
    val name: String
)

@Serializable
@Entity
data class SpaceStationType(
    val id: Int,
    val name: String
)

