package com.example.streamingapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamingapp.domain.usecase.GetSoundUseCase
import com.example.streamingapp.domain.usecase.GetSoundsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSoundsUseCase: GetSoundsUseCase,
    private val getSoundUseCase: GetSoundUseCase
): ViewModel() {

    fun searchAllSounds(query: String) {
        viewModelScope.launch {
            val result = getSoundsUseCase(query)
            Log.e("PROBANDO","$result")
            result?.forEach { each -> Log.e("PROBANDO","$each") }
        }
    }

    fun searchSoundById(id: Int) {
        viewModelScope.launch {
            val result = getSoundUseCase(id.toString())
            Log.e("PROBANDO","$id ---> $result")
        }
    }
}
