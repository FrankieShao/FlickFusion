package feature.main.list

import androidx.compose.ui.util.fastDistinctBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.repo.MovieRepo
import org.real.flickfusion.repo.TVRepo
import org.real.flickfusion.ui.DisplayItem
import org.real.flickfusion.ui.IUiState
import org.real.flickfusion.ui.toDisplayItem
import kotlin.reflect.KClass

/**
 * @author Frank Shao
 * @created 07/06/2024
 * Description: Category List ViewModel
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

class CategoryListViewModel(
    private val category: String,
    private val mediaType: String,
) : ViewModel(), KoinComponent {

    private val tvRepo: TVRepo by inject()
    private val movieRepo: MovieRepo by inject()
    private val configureRepo: ConfigureRepo by inject()

    private val _state = MutableStateFlow<UiState>(UiState.NoData(isRefreshing = true))
    val state: StateFlow<UiState> = _state

    init {
        refresh()
    }

    fun refresh() {
        _state.value = if (_state.value is UiState.HasData) {
            (_state.value as UiState.HasData).copy(
                isRefreshing = true,
                isLoadingMore = false,
                noMoreData = false
            )
        } else {
            UiState.NoData(isRefreshing = true)
        }
        viewModelScope.launch {
            getUseCase().invoke(1).map { result ->
                val configure = configureRepo.get()
                result.map { medias ->
                    medias.map {
                        // convert Media to DisplayItem
                        it.toDisplayItem(configure)
                    }
                }
            }.collect { result ->
                _state.value = if (result.isSuccess) {
                    val data = result.getOrNull()
                    if (data.isNullOrEmpty()) {
                        UiState.NoData(isEmpty = true)
                    } else {
                        UiState.HasData(data = data, page = 1)
                    }
                } else {
                    UiState.NoData(
                        isError = true,
                        errorMessage = result.exceptionOrNull()?.message
                    )
                }
            }
        }
    }

    fun loadMore() {
        val lastUiState = _state.value as? UiState.HasData ?: return
        val lastPage = lastUiState.page
        viewModelScope.launch {
            _state.value =
                lastUiState.copy(isLoadingMore = true, isRefreshing = false, noMoreData = false)
            getUseCase().invoke(lastPage + 1).map { result ->
                val configure = configureRepo.get()
                result.map { medias ->
                    medias.map {
                        // convert Media to DisplayItem
                        it.toDisplayItem(configure)
                    }
                }
            }.collect { result ->
                _state.value = if (result.isSuccess) {
                    val data = result.getOrNull()
                    if (data.isNullOrEmpty()) {
                        lastUiState.copy(
                            noMoreData = true,
                            isLoadingMore = false,
                            isRefreshing = false
                        )
                    } else {
                        lastUiState.copy(
                            data = (lastUiState.data + data).fastDistinctBy { it.id },
                            page = lastPage + 1,
                            isLoadingMore = false,
                            isRefreshing = false,
                            noMoreData = false
                        )
                    }
                } else {
                    lastUiState.copy(
                        isLoadingMore = false,
                        isRefreshing = false,
                        noMoreData = false
                    )
                }
            }
        }

    }

    private fun getUseCase() = findUseCase(category, mediaType, movieRepo, tvRepo)

    companion object {
        fun provideFactory(
            category: String,
            mediaType: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(
                modelClass: KClass<T>,
                extras: CreationExtras,
            ): T {
                return CategoryListViewModel(category, mediaType) as T
            }
        }
    }
}