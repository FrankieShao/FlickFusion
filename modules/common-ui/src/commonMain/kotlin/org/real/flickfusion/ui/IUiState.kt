package org.real.flickfusion.ui

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: basic interface for ui state
 */

interface IUiState {
    val isRefreshing: Boolean
    val isLoadingMore: Boolean
    val isError: Boolean
    val isEmpty: Boolean
    val errorMessage: String?
}