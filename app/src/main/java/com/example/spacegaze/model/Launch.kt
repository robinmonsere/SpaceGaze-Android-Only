package com.example.spacegaze.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Entity(tableName = "launches")
data class Launch(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    @Embedded(prefix = "status_")
    val status: LaunchStatus?,
    val net: String,
    @SerialName("lsp_name")
    val lspName: String?,
    val mission: String?,
    var isUpcoming: Boolean = false
)

@Serializable
data class LaunchList(
    @SerialName("results")
    val launches: List<Launch>,
)

@Entity
@Serializable
data class LaunchStatus(
    val name: String,
    val abbrev: String,
    val description: String
)