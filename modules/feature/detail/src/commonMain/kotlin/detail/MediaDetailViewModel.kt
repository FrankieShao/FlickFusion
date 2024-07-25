package detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.real.flickfusion.model.MediaModel
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.ui.IUiState
import org.real.flickfusion.usecase.MediaDetailUseCase
import kotlin.reflect.KClass

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: Media Detail ViewModel
 */
sealed interface UiState : IUiState {
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
        val data: MediaModel,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false
    ) : UiState {
        override val isLoadingMore = false
        override val errorMessage = null
    }
}

interface IMediaDetailViewModel {
    val state: StateFlow<UiState>
}

class MediaDetailViewModel(
    private val mediaType: MediaType,
    private val mediaId: Int
) : ViewModel(), KoinComponent, IMediaDetailViewModel {

    private val mediaDetailUseCase: MediaDetailUseCase by inject()

    private val _state = MutableStateFlow<UiState>(UiState.NoData(isRefreshing = true))
    override val state: StateFlow<UiState> = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = UiState.NoData(isRefreshing = true)
            try {
                mediaDetailUseCase(mediaType, mediaId).let { mediaModel ->
                    _state.value = UiState.HasData(isRefreshing = false, data = mediaModel)
                }
            } catch (e: Exception) {
                _state.value =
                    UiState.NoData(isRefreshing = false, isError = true, errorMessage = e.message)
            }
        }
    }

    companion object {
        fun provideFactory(
            mediaType: MediaType,
            mediaId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                return MediaDetailViewModel(mediaType, mediaId) as T
            }
        }
    }
}