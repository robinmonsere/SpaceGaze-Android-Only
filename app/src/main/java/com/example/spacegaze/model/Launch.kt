package com.example.spacegaze.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.ProtocolFamily

@Serializable
data class LaunchList(
    @SerialName("results")
    val launches: List<Launch>,
)

@Serializable
@Entity(tableName = "launches")
data class Launch(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    @Embedded("status_")
    val status: LaunchStatus?,
    val net: String,
    @SerialName("launch_service_provider")
    @Embedded("lsp_")
    val lsp: LaunchServiceProvider?,
    @Embedded("rocket_")
    val rocket: Rocket,
    @Embedded("mission_")
    val mission: Mission?,
    @Embedded("pad_")
    val pad: Pad?,
    val image: String?,
    var isUpcoming: Boolean = false
)

@Entity
@Serializable
data class LaunchStatus(
    val name: String,
    val abbrev: String,
    val description: String
)

@Entity
@Serializable
data class LaunchServiceProvider(
    val name: String,
    val type: String?
)

@Entity
@Serializable
data class Rocket(
    @Embedded
    val configuration : RocketConfiguration?
)

@Entity
@Serializable
data class RocketConfiguration(
    val name: String,
    val family: String?,
    @SerialName("full_name")
    val fullName: String?,
)

@Entity
@Serializable
data class Mission(
    val name: String?,
    val description: String?,
)

@Entity
@Serializable
data class Pad(
    val name: String?,
    @SerialName("map_url")
    val mapUrl: String?,
    @Embedded("loc_")
    val location: Location,
)

@Entity
@Serializable
data class Location(
    val name: String?
)

