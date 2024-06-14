package feature.mine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.model.AccountInfo
import core.domain.repo.ConfigureRepo
import core.domain.usecase.GetInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.IUiState

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: Mine ViewModel
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
        val data: AccountInfo,
        val urlPrefix: String,
        override val isError: Boolean = false,
        override val isEmpty: Boolean = false
    ) : UiState {
        override val isLoadingMore = false
        override val errorMessage = null
    }
}

class MineViewModel : ViewModel(), KoinComponent {

    private val accountUseCase: GetInfoUseCase by inject()
    private val configureProvider: ConfigureRepo by inject()

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
            accountUseCase().collect {
                when {
                    it.isSuccess -> {
                        _state.value = UiState.HasData(
                            isRefreshing = false,
                            data = it.getOrNull()!!,
                            urlPrefix = configureProvider.get()?.urlPrefix ?: ""
                        )
                    }

                    else -> {
                        _state.value = UiState.NoData(
                            isRefreshing = false,
                            isError = true,
                            errorMessage = it.exceptionOrNull()?.message ?: "Unknown error"
                        )
                    }
                }
            }
        }
    }
}