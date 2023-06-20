package com.example.streamingapp.data.repository

import androidx.paging.PagingData
import com.example.streamingapp.data.model.Sound
import kotlinx.coroutines.flow.Flow

interface PaginatedSoundsRepository {
    fun getPaginatedSoundsRepository(query: String) : Flow<PagingData<Sound>>
}
