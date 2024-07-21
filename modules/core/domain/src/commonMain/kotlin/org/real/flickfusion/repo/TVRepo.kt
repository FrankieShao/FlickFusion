package org.real.flickfusion.repo

import kotlinx.coroutines.flow.Flow
import org.real.flickfusion.model.Credits
import org.real.flickfusion.model.TVShow
import org.real.flickfusion.model.TVShowDetail

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: Gateway for TV
 */
interface TVRepo {
    fun getTrendingTVShows(): Flow<Result<List<TVShow>>>
    fun getPopularTVShows(page: Int): Flow<Result<List<TVShow>>>
    fun getTopRatedTVShows(page: Int): Flow<Result<List<TVShow>>>
    fun getOnTheAirTVShows(page: Int): Flow<Result<List<TVShow>>>
    fun getAiringTodayTVShows(page: Int): Flow<Result<List<TVShow>>>
    suspend fun getTVShowDetail(tvShowId: Int): TVShowDetail
    suspend fun getTVShowCredits(tvShowId: Int): Credits
}