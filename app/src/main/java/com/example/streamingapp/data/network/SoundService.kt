package com.example.streamingapp.data.network

import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.data.model.SoundResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SoundService @Inject constructor(
    private val api: SoundApiClient
) {
    suspend fun getSounds(query: String): List<Sound> {
        return withContext(Dispatchers.IO) {
            val response: Response<SoundResponse> = api.getAllSounds(query)
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getDetailedSound(id: String): DetailedSound? {
        return withContext(Dispatchers.IO) {
            val response: Response<DetailedSound> = api.getSound(id)
            response.body()
        }
    }
}
