package core.services.remote

import core.domain.model.Credits
import core.domain.model.Movie
import core.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 24/05/2024
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