package favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.ui.DisplayItem
import org.real.flickfusion.ui.IUiState
import org.real.flickfusion.ui.toDisplayItem
import org.real.flickfusion.usecase.FavoriteMovieUseCase
import org.real.flickfusion.usecase.FavoriteTvUseCase

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: Favorite Main ViewModel
 */

sealed interface UiState : IUiState {
    data class NoData(
        override val isRefreshing: Boolean = false,
        override val isLoadingMore: Boolean = false,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false,
        override val errorMessage: String? = null
    ) : UiState

    data class HasData(
        val data: List<DisplayItem>,
        val page: Int,
        val noMoreData: Boolean = false,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false,
        override val isRefreshing: Boolean = false,
        override val isLoadingMore: Boolean = false
    ) : UiState {
        override val errorMessage: String? = null
    }
}

interface IFavoriteMainViewModel {
    val state: StateFlow<UiState>
    fun refresh()
    fun loadMore()
}

class FavoriteMainViewModel : ViewModel(), KoinComponent, IFavoriteMainViewModel {

    private val favoriteMovie: FavoriteMovieUseCase by inject()
    private val favoriteTV: FavoriteTvUseCase by inject()
    private val configureRepo: ConfigureRepo by inject()

    private val _state = MutableStateFlow<UiState>(UiState.NoData(isRefreshing = true))
    override val state: StateFlow<UiState> = _state

    init {
        refresh()
    }

    override fun refresh() {
        viewModelScope.launch {
            _state.value = if (_state.value is UiState.HasData) {
                (_state.value as UiState.HasData).copy(
                    isRefreshing = true,
                    isLoadingMore = false,
                    noMoreData = false
                )
            } else {
                UiState.NoData(isRefreshing = true)
            }
            val configure = configureRepo.get()

            combine(favoriteMovie(1), favoriteTV(1)) { movies, tvs ->
                if (movies.isSuccess && tvs.isSuccess) {
                    val data = mutableListOf<DisplayItem>()
                    data.addAll((movies.getOrNull() ?: emptyList()).map {
                        it.toDisplayItem(configure)
                    })
                    data.addAll((tvs.getOrNull() ?: emptyList()).map {
                        it.toDisplayItem(configure)
                    })
                    data
                } else {
                    emptyList()
                }
            }.map {
                if (it.isEmpty()) {
                    UiState.NoData(isEmpty = true)
                } else {
                    UiState.HasData(data = it, page = 1)
                }
            } // todo handle error
                .collect {
                    _state.value = it
                }
        }
    }

    override fun loadMore() {
        val lastUiState = _state.value as? UiState.HasData ?: return

        viewModelScope.launch {
            _state.value =
                lastUiState.copy(isLoadingMore = true, isRefreshing = false, noMoreData = false)
            val lastPage = lastUiState.page
            val configure = configureRepo.get()
            combine(favoriteMovie(lastPage + 1), favoriteTV(lastPage + 1)) { movies, tvs ->
                if (movies.isSuccess && tvs.isSuccess) {
                    val data = mutableListOf<DisplayItem>()
                    data.addAll((movies.getOrNull() ?: emptyList()).map {
                        it.toDisplayItem(configure)
                    })
                    data.addAll((tvs.getOrNull() ?: emptyList()).map {
                        it.toDisplayItem(configure)
                    })
                    data
                } else {
                    emptyList()
                }
            }.map {
                if (it.isEmpty()) {
                    lastUiState.copy(
                        noMoreData = true,
                        isLoadingMore = false,
                        isRefreshing = false
                    )
                } else {
                    lastUiState.copy(
                        data = lastUiState.data + it,
                        page = lastPage + 1,
                        isLoadingMore = false,
                        isRefreshing = false,
                        noMoreData = false
                    )
                }
            }.collect {
                _state.value = it
            }
        }
    }
}