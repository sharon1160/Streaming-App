package com.example.streamingapp.ui.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.streamingapp.core.util.TestTags
import com.example.streamingapp.data.model.Sound
import com.example.streamingapp.mocks.ModelMocks
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun verifyWelcomeMessage() {
        composeRule.setContent {
            HomeScreenContent(
                query = "",
                updateQuery = {},
                paginatedSounds = flowOf(PagingData.empty<Sound>()).collectAsLazyPagingItems(),
                searchAllSounds = {},
                navigateToPlayer = {}
            )
        }
        composeRule.onNodeWithText("Welcome! Do a search").assertExists()
        composeRule.onNodeWithTag(TestTags.SOUNDS_LIST).assertDoesNotExist()
    }

    @Test
    fun verifyPaginatedSounds() {
        composeRule.setContent {
            HomeScreenContent(
                query = "Naruto",
                updateQuery = {},
                paginatedSounds = flowOf(PagingData.from(ModelMocks.soundsList)).collectAsLazyPagingItems(),
                searchAllSounds = {},
                navigateToPlayer = {}
            )
        }
        composeRule.onNodeWithText("Naruto").assertExists()
        composeRule.onNodeWithTag(TestTags.SOUNDS_LIST).assertExists()
        composeRule.onNodeWithText("Welcome! Do a search").assertDoesNotExist()
    }

    @Test
    fun verifyNavigateToPlayer() {
        var navigationClick = false
        composeRule.setContent {
            HomeScreenContent(
                query = "Naruto",
                updateQuery = {},
                paginatedSounds = flowOf(PagingData.from(ModelMocks.soundsList)).collectAsLazyPagingItems(),
                searchAllSounds = {},
                navigateToPlayer = { navigationClick = true }
            )
        }
        composeRule.onNodeWithTag(TestTags.SOUND_ITEM).performClick()
        assertTrue(navigationClick)
    }
}
