package com.example.streamingapp.ui.player

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.streamingapp.core.util.TestTags
import com.example.streamingapp.mocks.ModelMocks
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class PlayerScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verifyLoadingMessage() {
        composeRule.setContent {
            PlayerScreenContent(
                navigationToBack = { true },
                currentSound = null,
                isPlaying = false,
                playPauseSound = {},
                fastForward = {},
                rewind = {},
                currentPosition = 0L
            )
        }
        composeRule.onNodeWithTag(TestTags.LOADING_MESSAGE).assertExists()
        composeRule.onNodeWithTag(TestTags.MUSIC_DETAILS).assertDoesNotExist()
    }

    @Test
    fun verifyMusicPlayer() {
        composeRule.setContent {
            PlayerScreenContent(
                navigationToBack = { true },
                currentSound = ModelMocks.detailedSound,
                isPlaying = false,
                playPauseSound = {},
                fastForward = {},
                rewind = {},
                currentPosition = 0L
            )
        }
        composeRule.onNodeWithTag(TestTags.LOADING_MESSAGE).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.MUSIC_DETAILS).assertExists()
    }

    @Test
    fun verifyNavigationToBack() {
        var navigationClick = false
        composeRule.setContent {
            PlayerScreenContent(
                navigationToBack = {
                    navigationClick = true
                    true
                },
                currentSound = ModelMocks.detailedSound,
                isPlaying = false,
                playPauseSound = {},
                fastForward = {},
                rewind = {},
                currentPosition = 0L
            )
        }
        composeRule.onNodeWithContentDescription("Arrow back content description").performClick()
        assertTrue(navigationClick)
    }

    @Test
    fun verifyPlayPauseButton() {
        var playPauseClick = false
        composeRule.setContent {
            PlayerScreenContent(
                navigationToBack = { true },
                currentSound = ModelMocks.detailedSound,
                isPlaying = false,
                playPauseSound = { playPauseClick = true },
                fastForward = {},
                rewind = {},
                currentPosition = 0L
            )
        }
        composeRule.onNodeWithContentDescription("Play/Pause Icon").performClick()
        assertTrue(playPauseClick)
    }
}
