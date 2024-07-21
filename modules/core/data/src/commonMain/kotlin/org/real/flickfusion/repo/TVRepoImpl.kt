package org.real.flickfusion.repo

import org.real.flickfusion.remote.TVGateway
/**
 * @author Frank Shao
 * @created 20/07/2024
 * Description:
 */
class TVRepoImpl(private val gateway: TVGateway) : TVRepo {
    override fun getTrendingTVShows() = gateway.getTrendingTVShows()
    override fun getPopularTVShows(page: Int) = gateway.getPopularTVShows(page)
    override fun getTopRatedTVShows(page: Int) = gateway.getTopRatedTVShows(page)
    override fun getOnTheAirTVShows(page: Int) = gateway.getOnTheAirTVShows(page)
    override fun getAiringTodayTVShows(page: Int) = gateway.getAiringTodayTVShows(page)
    override suspend fun getTVShowDetail(tvShowId: Int) = gateway.getTVShowDetail(tvShowId)
    override suspend fun getTVShowCredits(tvShowId: Int) = gateway.getTVShowCredits(tvShowId)
}