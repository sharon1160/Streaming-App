package com.example.streamingapp.domain.usecase

import com.example.streamingapp.data.SoundRepository
import com.example.streamingapp.data.model.Sound
import javax.inject.Inject

class GetSoundsUseCase @Inject constructor(
    private val repository: SoundRepository
) {

    suspend operator fun invoke(query: String): List<Sound>? = repository.getAllSounds(query)
}
