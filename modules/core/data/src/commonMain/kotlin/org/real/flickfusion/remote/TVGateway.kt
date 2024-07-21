package org.real.flickfusion.remote

import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.Flow
import net.IHttp
import org.real.flickfusion.API
import org.real.flickfusion.UrlFormatter
import org.real.flickfusion.model.Credits
import org.real.flickfusion.model.TVShow
import org.real.flickfusion.model.TVShowDetail

/**
 * @author Frank Shao
 * @created 26/05/2024
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

class TVGatewayImpl constructor(
    private val client: IHttp
) : TVGateway {
    override fun getTrendingTVShows(): Flow<Result<List<TVShow>>> =
        getList(client, API.TV.TRENDING)

    override fun getPopularTVShows(page: Int): Flow<Result<List<TVShow>>> =
        getList(
            client,
            API.TV.POPULAR,
            mapOf("page" to page.toString())
        )

    override fun getTopRatedTVShows(page: Int): Flow<Result<List<TVShow>>> =
        getList(
            client,
            API.TV.TOP_RATED,
            mapOf("page" to page.toString())
        )

    override fun getOnTheAirTVShows(page: Int): Flow<Result<List<TVShow>>> =
        getList(
            client,
            API.TV.ON_THE_AIR,
            mapOf("page" to page.toString())
        )

    override fun getAiringTodayTVShows(page: Int): Flow<Result<List<TVShow>>> =
        getList(
            client,
            API.TV.AIRING_TODAY,
            mapOf("page" to page.toString())
        )

    override suspend fun getTVShowDetail(tvShowId: Int): TVShowDetail =
        client.get(url = "${API.TV.DETAIL}$tvShowId", typeInfo = typeInfo<TVShowDetail>())

    override suspend fun getTVShowCredits(tvShowId: Int): Credits =
        client.get(url = UrlFormatter(API.TV.CREDITS, tvShowId), typeInfo = typeInfo<Credits>())
}