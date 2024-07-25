package org.real.flickfusion.favorite.test

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.swipeUp
import androidx.test.ext.junit.runners.AndroidJUnit4
import favorite.FavoriteMainScreen
import favorite.IFavoriteMainViewModel
import favorite.UiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test
import org.junit.runner.RunWith
import org.real.flickfusion.test.fakeConfigure
import org.real.flickfusion.test.fakeMoviesPage1
import org.real.flickfusion.test.fakeMoviesPage2
import org.real.flickfusion.ui.DisplayItem
import org.real.flickfusion.ui.toDisplayItem

/**
 * @author Frank Shao
 * @created 25/07/2024
 * Description:
 */
@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class FavoriteScreenTest {

    private val viewModel: IFavoriteMainViewModel = mockk()

    @Test
    fun testError() = runComposeUiTest {
        every { viewModel.state } returns MutableStateFlow(UiState.NoData(isError = true))
        setContent {
            FavoriteMainScreen(viewModel) { }
        }
        waitForIdle()
        onNodeWithTag("CommonEmptyView").assertExists()
    }

    @Test
    fun testInitData() = runComposeUiTest {
        val fakeMovie = fakeMoviesPage1()
        val configure = fakeConfigure()
        val displayItems: List<DisplayItem> = fakeMovie.map { it.toDisplayItem(configure) }
        every { viewModel.state } returns MutableStateFlow(UiState.HasData(displayItems, 1, false))

        setContent {
            FavoriteMainScreen(viewModel) { }
        }
        waitForIdle()
        onNodeWithTag(fakeMovie[1].id.toString()).assertIsDisplayed()
        onNodeWithTag("LazyGrid").performScrollToIndex(fakeMovie.size - 3)

        waitForIdle()
        onNodeWithTag(fakeMovie[fakeMovie.size - 3].id.toString()).assertIsDisplayed()

    }

    @Test
    fun testLoadMoreData() = runComposeUiTest {
        val moviePage1 = fakeMoviesPage1()
        val moviePage2 = fakeMoviesPage2()
        val configure = fakeConfigure()
        val displayItemPage1: List<DisplayItem> = moviePage1.map { it.toDisplayItem(configure) }
        val displayItemPage2: List<DisplayItem> = moviePage2.map { it.toDisplayItem(configure) }
        val stateFlow = MutableStateFlow(UiState.HasData(displayItemPage1, 1, false))
        every { viewModel.state } returns stateFlow

        setContent {
            FavoriteMainScreen(viewModel) { }
        }
        val lazyGrid = onNodeWithTag("LazyGrid")
        onNodeWithTag(moviePage1[1].id.toString()).assertIsDisplayed()
        lazyGrid.performScrollToIndex(moviePage1.size - 1)
        waitForIdle()
        onNodeWithTag(moviePage1[moviePage1.size - 1].id.toString()).assertIsDisplayed()
        lazyGrid.performTouchInput {
            swipeUp(1000f, 800f)
        }
        val moreData = displayItemPage1 + displayItemPage2
        stateFlow.value = UiState.HasData(moreData, 2, false)

        lazyGrid.performScrollToIndex(moreData.size - 3)
        onNodeWithTag(moreData[moreData.size - 3].id).assertIsDisplayed()
    }
}
