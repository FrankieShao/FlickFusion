package feature.main.entity

import feature.main.DisplayMeta
import ui.DisplayItem
import ui.IUiState

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Feed UI State
 */
sealed interface FeedUiState : IUiState {

    data class NoData(
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false,
        override val errorMessage: String? = null,
    ) : FeedUiState {
        override val isLoadingMore: Boolean = false
    }

    data class HasData(
        override val isRefreshing: Boolean,
        val data: Map<DisplayMeta, List<DisplayItem>>,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false
    ) : FeedUiState {
        override val isLoadingMore = false
        override val errorMessage = null
    }
}