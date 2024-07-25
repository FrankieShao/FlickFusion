package org.real.flickfusion.main.test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.runComposeUiTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import feature.main.DisplayMeta
import feature.main.FeedType
import feature.main.entity.FeedUiState
import feature.main.movie.ui.MoviePage
import feature.main.movie.vm.IMovieFeedViewModel
import feature.main.tv.ui.TVPage
import feature.main.tv.vm.ITVFeedViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.real.flickfusion.model.MovieCategory
import org.real.flickfusion.model.TVCategory
import org.real.flickfusion.test.fakeConfigure
import org.real.flickfusion.test.fakeMoviesPage1
import org.real.flickfusion.test.fakeTVsPage1
import org.real.flickfusion.ui.DisplayItem
import org.real.flickfusion.ui.toDisplayItem

/**
 * @author Frank Shao
 * @created 25/07/2024
 * Description:
 */

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class MoviePageTest {
    private val viewModel: IMovieFeedViewModel = mockk()

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testEmpty() = runComposeUiTest {
        every { viewModel.state } returns MutableStateFlow(FeedUiState.NoData(isEmpty = true))
        setContent {
            MoviePage(viewModel = viewModel, onItemClick = {}, onEnterList = {})
        }
        waitForIdle()
        onNodeWithText("No Data, Try Again").assertExists()
    }

    @Test
    fun testError() = runComposeUiTest {
        every { viewModel.state } returns MutableStateFlow(FeedUiState.NoData(isError = true))
        setContent {
            MoviePage(viewModel = viewModel, onItemClick = {}, onEnterList = {})
        }
        waitForIdle()
        onNodeWithText("Something went wrong, Try Again").assertExists()
    }

    @Test
    fun testLoadSuccess() = runComposeUiTest {
        val fakeMovie = fakeMoviesPage1()
        val configure = fakeConfigure()
        val displayItems: List<DisplayItem> = fakeMovie.map { it.toDisplayItem(configure) }
        every { viewModel.state } returns MutableStateFlow(
            FeedUiState.HasData(
                isRefreshing = false,
                data = mapOf(
                    DisplayMeta(
                        MovieCategory.Trending,
                        FeedType.NORMAL,
                        false
                    ) to displayItems
                )
            )
        )
        setContent {
            MoviePage(viewModel = viewModel, onItemClick = {}, onEnterList = {})
        }
        waitForIdle()
        onNodeWithText("Trending").assertExists()
        val lazyRow = onNodeWithTag("LazyRow")
        lazyRow.performScrollToIndex(fakeMovie.size - 1)
        onNodeWithTag(fakeMovie[fakeMovie.size - 1].id.toString()).assertIsDisplayed()
    }
}

@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class TVPageTest {
    private val viewModel: ITVFeedViewModel = mockk()

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testEmpty() = runComposeUiTest {
        every { viewModel.state } returns MutableStateFlow(FeedUiState.NoData(isEmpty = true))
        setContent {
            TVPage(viewModel = viewModel, onItemClick = {}, onEnterList = {})
        }
        waitForIdle()
        onNodeWithText("No Data, Try Again").assertExists()
    }

    @Test
    fun testError() = runComposeUiTest {
        every { viewModel.state } returns MutableStateFlow(FeedUiState.NoData(isError = true))
        setContent {
            TVPage(viewModel = viewModel, onItemClick = {}, onEnterList = {})
        }
        waitForIdle()
        onNodeWithText("Something went wrong, Try Again").assertExists()
    }

    @Test
    fun testLoadSuccess() = runComposeUiTest {
        val fakeTVs = fakeTVsPage1()
        val configure = fakeConfigure()
        val displayItems: List<DisplayItem> = fakeTVs.map { it.toDisplayItem(configure) }
        every { viewModel.state } returns MutableStateFlow(
            FeedUiState.HasData(
                isRefreshing = false,
                data = mapOf(
                    DisplayMeta(
                        TVCategory.TopRated,
                        FeedType.NORMAL,
                        false
                    ) to displayItems
                )
            )
        )
        setContent {
            TVPage(viewModel = viewModel, onItemClick = {}, onEnterList = {})
        }
        waitForIdle()
        onNodeWithText("Top Rated").assertExists()
        val lazyRow = onNodeWithTag("LazyRow")
        lazyRow.performScrollToIndex(fakeTVs.size - 1)
        onNodeWithTag(fakeTVs[fakeTVs.size - 1].id.toString()).assertIsDisplayed()
    }
}