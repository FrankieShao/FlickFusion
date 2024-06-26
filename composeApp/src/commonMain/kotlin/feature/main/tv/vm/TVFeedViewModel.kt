package feature.main.tv.vm

import TrendingFeedType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.model.TVCategory
import core.domain.repo.ConfigureRepo
import core.domain.usecase.TVAiringTodayUseCase
import core.domain.usecase.TVOnTheAirUseCase
import core.domain.usecase.TVPopularUseCase
import core.domain.usecase.TVTopRatedUseCase
import core.domain.usecase.TVTrendingUseCase
import feature.main.DisplayMeta
import feature.main.entity.FeedUiState
import feature.main.entity.toDisplayItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.DisplayItem

/**
 * @author Frank Shao
 * @created 04/06/2024
 * Description: TV Feed ViewModel
 */
class TVFeedViewModel : ViewModel(), KoinComponent {

    private val trendingUseCase: TVTrendingUseCase by inject()
    private val airingTodayUseCase: TVAiringTodayUseCase by inject()
    private val popularUseCase: TVPopularUseCase by inject()
    private val topRatedUseCase: TVTopRatedUseCase by inject()
    private val onTheAirUseCase: TVOnTheAirUseCase by inject()
    private val configureRepo: ConfigureRepo by inject()
    private val _state = MutableStateFlow<FeedUiState>(FeedUiState.NoData(isRefreshing = true))
    val state: StateFlow<FeedUiState> = _state

    init {
        getData()
    }

    // merge vs combine
    fun getData() {
        // combine multiple data flow into one
        viewModelScope.launch {
            _state.value = if (_state.value is FeedUiState.HasData) {
                (_state.value as FeedUiState.HasData).copy(isRefreshing = true)
            } else {
                FeedUiState.NoData(isRefreshing = true)
            }
            combine(
                trendingUseCase(),
                airingTodayUseCase(1),
                onTheAirUseCase(1),
                popularUseCase(1),
                topRatedUseCase(1)
            ) { trending, airingToday, onTheAir, popular, topRated ->
                if (trending.isSuccess && airingToday.isSuccess
                    && onTheAir.isSuccess && popular.isSuccess && topRated.isSuccess
                ) {
                    val configure = configureRepo.get()
                    val data = mutableMapOf<DisplayMeta, List<DisplayItem>>()
                    data[DisplayMeta(TVCategory.Trending, TrendingFeedType(), false)] =
                        trending.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(TVCategory.AiringToday)] =
                        airingToday.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(TVCategory.Popular)] =
                        popular.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(TVCategory.TopRated)] =
                        topRated.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(TVCategory.OnTheAir)] =
                        onTheAir.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data
                } else {
                    //todo handle error
                    emptyMap()
                }
            }.map {
                if (it.isEmpty()) {
                    FeedUiState.NoData(isEmpty = true)
                } else {
                    FeedUiState.HasData(isRefreshing = false, data = it)
                }
            }.collect {
                _state.value = it
            }
        }
    }
}