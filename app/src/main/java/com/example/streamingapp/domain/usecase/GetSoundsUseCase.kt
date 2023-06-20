package com.example.streamingapp.domain.usecase

import com.example.streamingapp.data.repository.SoundRepository
import com.example.streamingapp.data.model.Sound
import javax.inject.Inject

class GetSoundsUseCase @Inject constructor(
    private val repository: SoundRepository
) {

    suspend operator fun invoke(query: String, page: String, pageSize: String): List<Sound>? =
        repository.getAllSounds(query, page, pageSize)
}
