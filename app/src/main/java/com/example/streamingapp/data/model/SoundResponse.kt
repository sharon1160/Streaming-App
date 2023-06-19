package com.example.streamingapp.data.model

import com.google.gson.annotations.SerializedName

class SoundResponse(
    @SerializedName("results")
    val results: List<Sound>
)
