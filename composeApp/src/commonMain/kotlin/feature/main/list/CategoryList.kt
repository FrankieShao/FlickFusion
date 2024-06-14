package feature.main.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import util.collectAsStateWithLifecycle
import flickfusion.composeapp.generated.resources.Res
import flickfusion.composeapp.generated.resources.ic_arrow
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import ui.DisplayItem
import ui.components.CommonEmptyView
import ui.components.SimplePosterDisplay
import ui.components.SmoothLazyColumn
import ui.components.refresh.rememberUltraSwipeRefreshState
import ui.theme.TITLE_WHITE

/**
 * @author Frank Shao
 * @created 07/06/2024
 * Description: Category List
 */
@Composable
fun CategoryList(
    category: String,
    mediaType: String,
    viewModel: CategoryListViewModel,
    onItemClicked: (DisplayItem) -> Unit = {},
    onNavBack: () -> Unit = {}
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        // title
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 20.dp, start = 10.dp)
        ) {
            val (title, returnButton) = createRefs()
            Icon(
                imageVector = vectorResource(Res.drawable.ic_arrow),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .clickable(onClick = onNavBack)
                    .graphicsLayer(scaleX = -1f)
                    .constrainAs(returnButton) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    })
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Center,
                text = "$category - $mediaType",
                style = MaterialTheme.typography.titleMedium,
                color = TITLE_WHITE,
                fontSize = 16.sp
            )
        }
        CategoryListContent(uiState = uiState,
            onLoadMore = { viewModel.loadMore() },
            onRefresh = { viewModel.refresh() },
            onItemClicked = { onItemClicked(it) })
    }
}

@Composable
fun CategoryListContent(
    uiState: UiState,
    onLoadMore: () -> Unit,
    onRefresh: () -> Unit,
    onItemClicked: (DisplayItem) -> Unit = {}
) {
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
        onRefresh = { onRefresh() }) {
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