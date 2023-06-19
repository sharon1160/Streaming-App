package com.example.streamingapp.data.network

import com.example.streamingapp.data.model.DetailedSound
import com.example.streamingapp.data.model.SoundResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SoundApiClient {

    //@GET("search/text/?query={query}&token=$TOKEN&page=$PAGE&page_size=$PAGE_SIZE&format=json")
    @GET("search/text/?")
    suspend fun getAllSounds(
        @Query("query") query: String,
        @Query("token") token: String = TOKEN,
        @Query("page") page: String = PAGE,
        @Query("page_size") pageSize: String = PAGE_SIZE,
        @Query("format") format: String = "json"
    ): Response<SoundResponse>

    @GET("sounds/{id}/?")
    suspend fun getSound(
        @Path("id") id: String,
        @Query("token") token: String = TOKEN,
        @Query("format") format: String = "json"
    ): Response<DetailedSound>

    companion object {
        const val TOKEN = "Frviaa2Vj7h8B2e13d4gZ5bOPr6jTljzJL34dbml"
        const val PAGE = "1"
        const val PAGE_SIZE = "2"
    }
}
