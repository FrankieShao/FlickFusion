package feature.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import util.collectAsStateWithLifecycle
import core.domain.model.MediaModel
import org.koin.compose.koinInject
import ui.DisplayItem
import ui.components.CommonEmptyView
import ui.components.SimplePosterDisplay
import ui.components.SmoothLazyColumn
import ui.components.refresh.rememberUltraSwipeRefreshState
import ui.theme.TITLE_WHITE

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: Favorite Main Screen
 */

@Composable
fun FavoriteMainScreen(onItemClicked: (DisplayItem) -> Unit = {}) {
    val viewModel: FavoriteMainViewModel = koinInject()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 19.dp),
            textAlign = TextAlign.Center,
            text = "Favorite",
            color = TITLE_WHITE,
            style = MaterialTheme.typography.titleMedium,
        )
        FavoriteList(
            uiState = uiState,
            onLoadMore = { viewModel.loadMore() },
            onRefresh = { viewModel.refresh() },
            onItemClicked = onItemClicked
        )
    }
}

@Composable
fun FavoriteList(uiState: UiState,
                 onLoadMore: () -> Unit,
                 onRefresh: () -> Unit,
                 onItemClicked: (DisplayItem) -> Unit = {}) {
    val state = rememberUltraSwipeRefreshState()
    when (uiState) {
        is UiState.NoData -> {
            state.isRefreshing = uiState.isRefreshing
        }

        is UiState.HasData -> {
            state.isRefreshing = uiState.isRefreshing
            state.isLoading = uiState.isLoadingMore
        }
    }
    SmoothLazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state,
        loadMoreEnabled = if (uiState is UiState.HasData) {
            !uiState.noMoreData
        } else {
            true
        },
        onLoadMore = { onLoadMore() },
        onRefresh = { onRefresh() }
    ) {
        when (uiState) {
            is UiState.HasData -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(count = uiState.data.size,
                        key = { index -> uiState.data[index].id }) { index ->
                        SimplePosterDisplay(modifier = Modifier
                            .width(70.dp)
                            .height(165.dp),
                            item = uiState.data[index],
                            onItemClick = { onItemClicked(uiState.data[index]) })
                    }
                }
            }

            is UiState.NoData -> {
                // show empty view
                CommonEmptyView(uiState = uiState)
            }
        }
    }
}


