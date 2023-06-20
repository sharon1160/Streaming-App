package com.example.streamingapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.data.paging.SoundsPagingSource
import com.example.streamingapp.domain.usecase.GetSoundsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaginatedSoundsRepositoryImpl @Inject constructor(
    private val getSoundsUseCase: GetSoundsUseCase
): PaginatedSoundsRepository {

    override fun getPaginatedSoundsRepository(query: String): Flow<PagingData<Sound>> = Pager(
        initialKey = null,
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = {
            SoundsPagingSource(
                getSoundsUseCase,
                query,
                PAGE_SIZE.toString()
            )
        }
    ).flow

    companion object {
        const val PAGE_SIZE = 10
    }
}
