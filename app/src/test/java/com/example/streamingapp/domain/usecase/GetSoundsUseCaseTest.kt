package com.example.streamingapp.domain.usecase

import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.data.repository.SoundRepository
import com.example.streamingapp.mocks.DomainConstantsMocks
import com.example.streamingapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class GetSoundsUseCaseTest {
    @RelaxedMockK
    lateinit var repository: SoundRepository

    lateinit var getSoundsUseCase: GetSoundsUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getSoundsUseCase = GetSoundsUseCase(repository)
    }

    @Test
    fun `when the api returns an empty list`() = runTest {
        // Given
        coEvery {
            repository.getAllSounds(
                query = any(),
                page = any(),
                pageSize = any()
            )
        } returns emptyList()

        // When
        val response = getSoundsUseCase.invoke(
            query = DomainConstantsMocks.QUERY,
            page = DomainConstantsMocks.PAGE,
            pageSize = DomainConstantsMocks.PAGE_SIZE
        )

        // Then
        coVerify(exactly = 1) {
            repository.getAllSounds(any(), any(), any())
        }
        assertNotNull(response)
        response?.let { soundsList ->
            assertEquals(emptyList<Sound>()::javaClass.name, soundsList::javaClass.name)
            assertEquals(emptyList<Sound>(), soundsList)
        }
    }

    @Test
    fun `when the api returns an sounds list`() = runTest {
        // Given
        val soundsList = DomainModelMocks.soundsList
        coEvery { repository.getAllSounds(any(), any(), any()) } returns soundsList

        // When
        val response = getSoundsUseCase.invoke(
            query = DomainConstantsMocks.QUERY,
            page = DomainConstantsMocks.PAGE,
            pageSize = DomainConstantsMocks.PAGE_SIZE
        )

        // Then
        coVerify(exactly = 1) {
            repository.getAllSounds(any(), any(), any())
        }
        assertNotNull(response)
        response?.let {
            assertEquals(soundsList::javaClass.name, response::javaClass.name)
            assertEquals(soundsList, response)
        }
    }
}
