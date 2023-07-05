package com.example.streamingapp.domain.usecase

import com.example.streamingapp.data.repository.SoundRepository
import com.example.streamingapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test


class GetSoundUseCaseTest {
    @RelaxedMockK
    lateinit var repository: SoundRepository

    lateinit var getSoundUseCase: GetSoundUseCase

    private val soundId = com.example.streamingapp.mocks.DomainConstantsMocks.SOUND_ID

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getSoundUseCase = GetSoundUseCase(repository)
    }

    @Test
    fun `when the api returns null`() = runTest{
        // Given
        coEvery { repository.getSound(id = any()) } returns null

        // When
        val response = getSoundUseCase.invoke(id = soundId)

        // Then
        coVerify(exactly = 1) { repository.getSound(id = soundId) }
        assertNull(response)
    }

    @Test
    fun `when the api returns something`() = runTest {
        // Given
        val detailedSound = DomainModelMocks.detailedSoundMock
        coEvery { repository.getSound(any()) } returns detailedSound

        // When
        val response = getSoundUseCase.invoke(id = soundId)

        // Then
        coVerify(exactly = 1) { repository.getSound(id = soundId) }
        assertNotNull(response)
        response?.let {
            assertEquals(detailedSound::javaClass.name, response::javaClass.name)
            assertEquals(detailedSound.id, response.id)
            assertEquals(detailedSound.name, response.name)
            assertEquals(detailedSound.username, response.username)
            assertEquals(detailedSound.description, response.description)
        }
    }
}
