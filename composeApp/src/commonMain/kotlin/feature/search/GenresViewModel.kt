package feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.model.Genre
import core.domain.usecase.GenreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.IUiState

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Genres ViewModel. provide various genres for user to select
 */

sealed interface UiState: IUiState {

    data class NoData(
        override val isRefreshing: Boolean = false,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false,
        override val errorMessage: String? = null
    ) : UiState {
        override val isLoadingMore = false
    }

    data class HasData(
        override val isRefreshing: Boolean,
        val data: List<Genre>,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false
    ) : UiState {
        override val isLoadingMore = false
        override val errorMessage = null
    }
}

class GenresViewModel : ViewModel(), KoinComponent {

    val genresUseCase: GenreUseCase by inject()

    private val _state = MutableStateFlow<UiState>(UiState.NoData(isRefreshing = true))
    val state: StateFlow<UiState> = _state

    init {
        getData()
    }

    fun getData() {

        viewModelScope.launch {
            _state.value = if (_state.value is UiState.HasData) {
                (_state.value as UiState.HasData).copy(isRefreshing = true)
            } else {
                UiState.NoData(isRefreshing = true)
            }
            genresUseCase().map {
                if (it.isSuccess && it.getOrNull() != null) {
                    UiState.HasData(isRefreshing = false, data = it.getOrNull()!!)
                } else {
                    UiState.NoData(isEmpty = true)
                }
            }.collect{
                _state.value = it
            }
        }
    }
}