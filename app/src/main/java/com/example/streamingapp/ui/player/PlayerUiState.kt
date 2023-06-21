package com.example.streamingapp.ui.player

import com.example.streamingapp.data.model.DetailedSound

data class PlayerUiState(
    val currentSound: DetailedSound? = null,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0
)
