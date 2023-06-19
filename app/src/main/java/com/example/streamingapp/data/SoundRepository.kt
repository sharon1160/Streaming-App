package com.example.streamingapp.data

import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.data.model.SoundProvider
import com.example.streamingapp.data.network.SoundService
import javax.inject.Inject

class SoundRepository @Inject constructor(
    private val api: SoundService
) {
    suspend fun getAllSounds(query: String): List<Sound> {
        val response = api.getSounds(query)
        SoundProvider.sounds = response
        return response
    }

    suspend fun getSound(id: String): DetailedSound? {
        val response = api.getDetailedSound(id)
        SoundProvider.detailedSound = response
        return response
    }
}
