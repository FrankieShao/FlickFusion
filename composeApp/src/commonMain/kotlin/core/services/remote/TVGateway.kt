package core.services.remote

import core.domain.model.Credits
import core.domain.model.TVShow
import core.domain.model.TVShowDetail
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: Gateway for TV
 */
interface TVGateway {
    fun getTrendingTVShows(): Flow<Result<List<TVShow>>>
    fun getPopularTVShows(page: Int): Flow<Result<List<TVShow>>>
    fun getTopRatedTVShows(page: Int): Flow<Result<List<TVShow>>>
    fun getOnTheAirTVShows(page: Int): Flow<Result<List<TVShow>>>
    fun getAiringTodayTVShows(page: Int): Flow<Result<List<TVShow>>>
    suspend fun getTVShowDetail(tvShowId: Int): TVShowDetail
    suspend fun getTVShowCredits(tvShowId: Int): Credits
}
