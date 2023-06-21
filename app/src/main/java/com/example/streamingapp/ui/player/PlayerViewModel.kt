package com.example.streamingapp.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingapp.domain.usecase.GetSoundUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getSoundUseCase: GetSoundUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState = _uiState.asStateFlow()

    fun searchSoundById(id: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(currentSound = getSoundUseCase.invoke(id = id.toString()))
            }
        }
    }

    fun updateIsPlaying(newValue: Boolean) {
        _uiState.update {
            it.copy(isPlaying = newValue)
        }
    }
}
