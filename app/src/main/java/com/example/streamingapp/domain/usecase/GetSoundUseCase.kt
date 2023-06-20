package com.example.streamingapp.domain.usecase

import com.example.streamingapp.data.repository.SoundRepository
import com.example.streamingapp.data.model.DetailedSound
import javax.inject.Inject

class GetSoundUseCase @Inject constructor(
    private val repository: SoundRepository
) {
    suspend operator fun invoke(id: String): DetailedSound? = repository.getSound(id)
}
