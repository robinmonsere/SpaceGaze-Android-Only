package com.example.spacegaze.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LaunchList(
    @SerialName("results")
    val launches: List<Launch>,
)

@Serializable
data class Launch(
    val name: String,
    val status: LaunchStatus,
    val net: String,
)

@Serializable
data class LaunchStatus(
    val name: String,
    val abbrev: String,
    val description: String
)