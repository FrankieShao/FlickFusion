package framework.network.remote

import com.real.network.API
import framework.network.getList
import core.domain.model.Credits
import core.domain.model.Movie
import core.domain.model.MovieDetail
import core.services.remote.MovieGateway
import framework.network.UrlFormatter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 25/05/2024
 * Description: Gateway for movie
 */
class MovieGateWayImpl    constructor(
    private val client: HttpClient
) : MovieGateway {

    override fun getTrendingMovies(): Flow<Result<List<Movie>>> =
        getList(client, API.MOVIE.TRENDING)

    override fun getTopRatedMovies(page: Int): Flow<Result<List<Movie>>> =
        getList(
            client,
            API.MOVIE.TOP_RATED,
            mapOf("page" to page.toString())
        )

    override fun getPopularMovies(page: Int): Flow<Result<List<Movie>>> =
        getList(
            client,
            API.MOVIE.POPULAR,
            mapOf("page" to page.toString())
        )

    override fun getNowPlayingMovies(page: Int): Flow<Result<List<Movie>>> =
        getList(
            client,
            API.MOVIE.NOW_PLAYING,
            mapOf("page" to page.toString())
        )

    override fun getUpcomingMovies(page: Int): Flow<Result<List<Movie>>> =
        getList(
            client,
            API.MOVIE.UPCOMING,
            mapOf("page" to page.toString())
        )

    override suspend fun getMovieDetail(movieId: Int): MovieDetail =
        client.get("${API.MOVIE.DETAIL}$movieId").body()

    override suspend fun getMovieCredits(movieId: Int): Credits =
        client.get(UrlFormatter(API.MOVIE.CREDITS, movieId)).body()
}

