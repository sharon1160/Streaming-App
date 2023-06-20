package com.example.streamingapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.domain.usecase.GetSoundsUseCase

class SoundsPagingSource(
    private val getSoundsUseCase: GetSoundsUseCase,
    private val query: String,
    private val pageSize: String
): PagingSource<Int, Sound>() {

    override fun getRefreshKey(state: PagingState<Int, Sound>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Sound> {
        return try {
            val currentPage = params.key ?: 1
            val responseList: List<Sound> = getSoundsUseCase.invoke(
                page = currentPage.toString(),
                query = query,
                pageSize = pageSize
            ) ?: emptyList()

            return LoadResult.Page(
                data = responseList,
                prevKey = null,
                nextKey = if (responseList.isNotEmpty()) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
