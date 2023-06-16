package com.example.streamingapp.data.network

import com.example.streamingapp.core.RetrofitHelper
import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.data.model.Sound
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SoundService {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getSounds(query: String): List<Sound> {
        return withContext(Dispatchers.IO) {
            val response: Response<List<Sound>> =
                retrofit.create(SoundApiClient::class.java).getAllSounds(query)
            response.body() ?: emptyList()
        }
    }

    suspend fun getDetailedSound(id: String): DetailedSound? {
        return withContext(Dispatchers.IO) {
            val response: Response<DetailedSound> =
                retrofit.create(SoundApiClient::class.java).getSound(id)
            response.body()
        }
    }
}
