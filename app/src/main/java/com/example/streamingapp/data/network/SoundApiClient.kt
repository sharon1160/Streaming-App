package com.example.streamingapp.data.network

import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.data.model.Sound
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SoundApiClient {

    @GET(value = "search/text/?query={query}&token=$TOKEN")
    suspend fun getAllSounds(@Query("query") query: String): Response<List<Sound>>

    @GET(value = "sounds/{id}/?token=$TOKEN")
    suspend fun getSound(@Query("id") id: String): Response<DetailedSound>

    companion object {
        const val TOKEN = "Frviaa2Vj7h8B2e13d4gZ5bOPr6jTljzJL34dbml"
    }
}
