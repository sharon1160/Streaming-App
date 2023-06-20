package com.example.streamingapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.data.repository.PaginatedSoundsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val paginatedSoundsRepository: PaginatedSoundsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _paginatedSounds = MutableStateFlow<PagingData<Sound>>(PagingData.empty())
    val paginatedSounds = _paginatedSounds.cachedIn(viewModelScope)

    fun searchAllSounds(query: String) {
        paginatedSoundsRepository.getPaginatedSoundsRepository(query).onEach { paginatedSounds ->
            _paginatedSounds.update {
                paginatedSounds
            }
        }.launchIn(viewModelScope)
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun updateQuery(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }
}
