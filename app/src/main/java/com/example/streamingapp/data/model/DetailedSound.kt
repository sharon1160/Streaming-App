package com.example.streamingapp.data.model

data class DetailedSound(
    val id: Int,
    val name: String,
    val description: String,
    val username: String,
    val duration: Float,
    val previews: Map<String, String>,
    val images: Map<String, String>,
)
