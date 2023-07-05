package com.example.streamingapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import com.example.streamingapp.data.repository.PaginatedSoundsRepository
import com.example.streamingapp.mocks.DomainConstantsMocks
import com.example.streamingapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeViewModelTest {

    @RelaxedMockK
    private lateinit var paginatedSoundsRepository: PaginatedSoundsRepository

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        homeViewModel = HomeViewModel(paginatedSoundsRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when searchAllSounds should update the paginatedSounds and uiState`() = runTest {
        val paginatedSounds = PagingData.from(DomainModelMocks.soundsList)
        coEvery { paginatedSoundsRepository.getPaginatedSoundsRepository(any()) } returns flowOf(
            paginatedSounds
        )

        homeViewModel.searchAllSounds(DomainConstantsMocks.QUERY)

        val expectedUiState = HomeUiState(query = DomainConstantsMocks.QUERY)

        assertEquals(homeViewModel.uiState.value, expectedUiState)
        assertEquals(
            homeViewModel.paginatedSounds.first()::javaClass.name, paginatedSounds::javaClass.name
        )
        val job = launch {
            homeViewModel.paginatedSounds.collect { soundsPaging ->
                assertEquals(soundsPaging.toDataList(), paginatedSounds.toDataList())
            }
        }
        delay(500)
        job.cancel()
    }
}

private fun <Sound : Any> PagingData<Sound>.toDataList(): List<Sound> {
    val soundsList = mutableListOf<Sound>()
    this.map { soundsList.add(it) }
    return soundsList
}
