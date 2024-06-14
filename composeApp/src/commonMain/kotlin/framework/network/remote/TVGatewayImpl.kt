package framework.network.remote

import com.real.network.API
import framework.network.getList
import core.domain.model.Credits
import core.domain.model.TVShow
import core.domain.model.TVShowDetail
import core.services.remote.TVGateway
import framework.network.UrlFormatter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: Gateway for TV
 */
class TVGatewayImpl constructor(
    private val client: HttpClient
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
        client.get("${API.TV.DETAIL}$tvShowId").body()

    override suspend fun getTVShowCredits(tvShowId: Int): Credits =
        client.get(UrlFormatter(API.TV.CREDITS, tvShowId)).body()
}