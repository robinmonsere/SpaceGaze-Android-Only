package com.example.spacegaze.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Launch(
    val url: String,
    val name: String,
    val net: String
)