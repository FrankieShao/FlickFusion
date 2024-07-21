package org.real.flickfusion.remote

import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.Flow
import net.IHttp
import org.real.flickfusion.API
import org.real.flickfusion.UrlFormatter
import org.real.flickfusion.model.Credits
import org.real.flickfusion.model.Movie
import org.real.flickfusion.model.MovieDetail

/**
 * @author Frank Shao
 * @created 25/05/2024
 * Description: Gateway for movie
 */
interface MovieGateway {
    fun getTrendingMovies(): Flow<Result<List<Movie>>>
    fun getTopRatedMovies(page: Int): Flow<Result<List<Movie>>>
    fun getPopularMovies(page: Int): Flow<Result<List<Movie>>>
    fun getNowPlayingMovies(page: Int): Flow<Result<List<Movie>>>
    fun getUpcomingMovies(page: Int): Flow<Result<List<Movie>>>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getMovieCredits(movieId: Int): Credits
}

class MovieGateWayImpl constructor(
    private val client: IHttp
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
        client.get(url = "${API.MOVIE.DETAIL}$movieId", typeInfo = typeInfo<MovieDetail>())

    override suspend fun getMovieCredits(movieId: Int): Credits =
        client.get(url = UrlFormatter(API.MOVIE.CREDITS, movieId), typeInfo = typeInfo<Credits>())
}

