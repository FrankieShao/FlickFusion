package org.real.flickfusion.feature.detail

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import detail.IMediaDetailViewModel
import detail.MediaDetailScreen
import detail.UiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.real.flickfusion.test.fakeMediaModel
import kotlin.test.Test

class MediaDetailScreenTest {

    private val viewModel: IMediaDetailViewModel = mockk()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testLoading() = runComposeUiTest {
        every { viewModel.state } returns MutableStateFlow(UiState.NoData(isRefreshing = true))
        setContent {
            MediaDetailScreen(viewModel){ }
        }
        onNodeWithTag("CommonLoadingView").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testError() = runComposeUiTest {
        every { viewModel.state } returns MutableStateFlow(UiState.NoData(isError = true))
        setContent {
            MediaDetailScreen(viewModel){ }
        }
        onNodeWithText("Something went wrong, Try Again").assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testWithData() = runComposeUiTest {
        val mediaModel = fakeMediaModel()
        every { viewModel.state } returns MutableStateFlow(UiState.HasData(isRefreshing = false, mediaModel))
        setContent {
            MediaDetailScreen(viewModel){ }
        }
        onNodeWithText(mediaModel.title).assertExists()
        onNodeWithText(mediaModel.type.name).assertExists()
        onNodeWithText(mediaModel.releaseDate).assertExists()
        onNodeWithText(mediaModel.overview).assertExists()
    }

}