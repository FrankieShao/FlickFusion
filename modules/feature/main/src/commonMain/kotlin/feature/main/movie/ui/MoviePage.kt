package feature.main.movie.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import feature.main.component.DisplayFeed
import feature.main.entity.FeedUiState
import feature.main.movie.vm.IMovieFeedViewModel
import feature.main.movie.vm.MovieFeedViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.real.flickfusion.util.collectAsStateWithLifecycle
import org.real.flickfusion.ui.components.SmoothLazyColumn
import org.real.flickfusion.ui.widgets.refresh.rememberUltraSwipeRefreshState
import org.real.flickfusion.ui.theme.SELECTED_COLOR

/**
 * @author Frank Shao
 * @created 04/06/2024
 * Description: Movie Page
 */
@Composable
fun MoviePage(
    modifier: Modifier = Modifier,
    viewModel: IMovieFeedViewModel,
    onItemClick: (String) -> Unit,
    onEnterList: (String) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    MoviePageMain(
        modifier = modifier,
        uiState = uiState,
        onRefresh = { viewModel.getData() },
        onItemClick = { onItemClick(it) },
        onEnterList = { onEnterList(it) }
    )
}

@Composable
private fun MoviePageMain(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    onRefresh: () -> Unit,
    onItemClick: (String) -> Unit,
    onEnterList: (String) -> Unit
) {
    val state = rememberUltraSwipeRefreshState()
    state.isRefreshing = uiState.isRefreshing
    SmoothLazyColumn(
        modifier = modifier.fillMaxSize(),
        state = state,
        loadMoreEnabled = false,
        onRefresh = { onRefresh() }) {
        if (uiState is FeedUiState.HasData) {
            DisplayFeed(
                map = (uiState as FeedUiState.HasData).data,
                onItemClick = { onItemClick(it) },
                onEnterList = { onEnterList(it) }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when {
                        uiState.isEmpty -> "No Data, Try Again"
                        uiState.isError -> "Something went wrong, Try Again"
                        else -> "Loading"
                    },
                    color = SELECTED_COLOR,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .wrapContentSize(),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
@Preview()
fun MoviePagePreview() {
    MoviePageMain(
        uiState = FeedUiState.NoData(isError = true),
        onRefresh = {},
        onItemClick = {},
        onEnterList = {}
    )
}