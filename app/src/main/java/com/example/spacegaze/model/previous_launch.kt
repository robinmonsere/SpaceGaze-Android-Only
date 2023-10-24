package com.example.spacegaze.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "previous_launches")
data class PreviousLaunch(
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
)