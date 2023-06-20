package com.example.streamingapp.data.network

import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.data.model.SoundResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SoundApiClient {

    @GET("search/text/?")
    suspend fun getAllSounds(
        @Query("query") query: String,
        @Query("page") page: String = PAGE,
        @Query("page_size") pageSize: String = PAGE_SIZE,
        @Query("format") format: String = FORMAT
    ): Response<SoundResponse>

    @GET("sounds/{id}/?")
    suspend fun getSound(
        @Path("id") id: String,
        @Query("format") format: String = FORMAT
    ): Response<DetailedSound>

    companion object {
        const val PAGE = "1"
        const val PAGE_SIZE = "10"
        const val FORMAT = "json"
    }
}
