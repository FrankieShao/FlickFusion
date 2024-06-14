package feature.main.tv.ui

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
import feature.main.tv.vm.TVFeedViewModel
import org.koin.compose.koinInject
import ui.components.SmoothLazyColumn
import ui.components.refresh.rememberUltraSwipeRefreshState
import ui.theme.SELECTED_COLOR
import util.collectAsStateWithLifecycle

/**
 * @author Frank Shao
 * @created 04/06/2024
 * Description: TV Page
 */
@Composable
fun TVPage(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    onEnterList: (String) -> Unit
) {
    val viewModel: TVFeedViewModel = koinInject()
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val state = rememberUltraSwipeRefreshState()
    state.isRefreshing = uiState.isRefreshing
    SmoothLazyColumn(
        modifier = modifier.fillMaxSize(),
        state = state,
        loadMoreEnabled = false,
        onRefresh = { viewModel.getData() }) {
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