package com.example.streamingapp.ui.player

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.media3.exoplayer.ExoPlayer
import com.example.streamingapp.domain.usecase.GetSoundUseCase
import com.example.streamingapp.mocks.DomainConstantsMocks
import com.example.streamingapp.mocks.DomainModelMocks
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class PlayerViewModelTest {

    private lateinit var getSoundUseCase: GetSoundUseCase

    private lateinit var player: ExoPlayer

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var playerViewModel: PlayerViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        getSoundUseCase = mockk(relaxed = true)
        player = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle()
        savedStateHandle["id"] = 1
        playerViewModel = PlayerViewModel(getSoundUseCase, player, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when searchSoundById should update the currentSound`() = runTest {
        every { player.isPlaying } returns true

        coEvery { getSoundUseCase.invoke(any()) } returns DomainModelMocks.detailedSoundMock

        playerViewModel.searchSoundById(DomainConstantsMocks.SOUND_ID.toInt())

        val expectedUiState = PlayerUiState(currentSound = DomainModelMocks.detailedSoundMock)

        assertEquals(playerViewModel.uiState.value, expectedUiState)
    }

    @Test
    fun `test loadSound`() = runTest {
        val sound = DomainModelMocks.detailedSoundMock
        every { player.isPlaying } returns true

        every { player.setMediaItem(any()) } just Runs
        every { player.prepare() } just Runs

        playerViewModel.loadSound(sound)

        verify { player.setMediaItem(any()) }
        verify { player.prepare() }
    }
}
