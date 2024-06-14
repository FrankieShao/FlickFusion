package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ui.components.refresh.UltraSwipeRefresh
import ui.components.refresh.UltraSwipeRefreshState
import ui.components.refresh.lottie.LottieRefreshFooter
import ui.components.refresh.lottie.LottieRefreshHeader
import ui.components.refresh.rememberUltraSwipeRefreshState

/**
 * @author Frank Shao
 * @created 11/06/2024
 * Description: Smooth Lazy Column.
 * built with UltraSwipeRefresh
 */

@Composable
fun SmoothLazyColumn(
    modifier: Modifier = Modifier,
    state: UltraSwipeRefreshState,
    loadMoreEnabled: Boolean = false,
    onLoadMore: () -> Unit = {},
    onRefresh: () -> Unit = {},
    content: @Composable () -> Unit
) {
    UltraSwipeRefresh(
        modifier = modifier,
        state = state,
        loadMoreEnabled = loadMoreEnabled,
        onRefresh = {
            onRefresh()
        },
        onLoadMore = {
            onLoadMore()
        },
        headerIndicator = {
            LottieRefreshHeader(it)
        },
        footerIndicator = {
            LottieRefreshFooter(it)
        }
    ) {
        content()
    }
}