package feature.main.movie.vm

import TrendingFeedType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.model.MovieCategory
import core.domain.repo.ConfigureRepo
import core.domain.usecase.MovieNowPlayingUseCase
import core.domain.usecase.MoviePopularUseCase
import core.domain.usecase.MovieTopRatedUseCase
import core.domain.usecase.MovieTrendingUseCase
import core.domain.usecase.MovieUpcomingUseCase
import feature.main.DisplayMeta
import feature.main.FeedType
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
 * @created 02/06/2024
 * Description: ViewModel for Main Movie Page
 */

class MovieFeedViewModel : ViewModel(), KoinComponent {

    private val trendingUseCase: MovieTrendingUseCase by inject()
    private val nowPlayingUseCase: MovieNowPlayingUseCase by inject()
    private val popularUseCase: MoviePopularUseCase by inject()
    private val topRatedUseCase: MovieTopRatedUseCase by inject()
    private val upcomingUseCase: MovieUpcomingUseCase by inject()
    private val configureRepo: ConfigureRepo by inject()

    private val _state = MutableStateFlow<FeedUiState>(FeedUiState.NoData(isRefreshing = true))
    val state: StateFlow<FeedUiState> = _state

    init {
        getData()
    }

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
                nowPlayingUseCase(1),
                upcomingUseCase(1),
                popularUseCase(1),
                topRatedUseCase(1)
            ) { trending, nowPlaying, upcoming, popular, topRated ->
                if (trending.isSuccess && nowPlaying.isSuccess
                    && upcoming.isSuccess && popular.isSuccess && topRated.isSuccess
                ) {
                    val configure = configureRepo.get()
                    val data = mutableMapOf<DisplayMeta, List<DisplayItem>>()
                    data[DisplayMeta(MovieCategory.Trending, TrendingFeedType(), false)] =
                        trending.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(MovieCategory.NowPlaying)] =
                        nowPlaying.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(MovieCategory.Upcoming)] =
                        upcoming.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(MovieCategory.Popular)] =
                        popular.getOrNull()!!.map { it.toDisplayItem(configure) }
                    data[DisplayMeta(MovieCategory.TopRated)] =
                        topRated.getOrNull()!!.map { it.toDisplayItem(configure) }
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
