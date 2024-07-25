package org.real.flickfusion.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import org.real.flickfusion.ui.widgets.refresh.UltraSwipeRefresh
import org.real.flickfusion.ui.widgets.refresh.UltraSwipeRefreshState
import org.real.flickfusion.ui.widgets.refresh.lottie.LottieRefreshFooter
import org.real.flickfusion.ui.widgets.refresh.lottie.LottieRefreshHeader

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
            LottieRefreshHeader(state = it, Modifier.testTag("RefreshHeader"))
        },
        footerIndicator = {
            LottieRefreshFooter(state = it, Modifier.testTag("LoadFooter"))
        }
    ) {
        content()
    }
}