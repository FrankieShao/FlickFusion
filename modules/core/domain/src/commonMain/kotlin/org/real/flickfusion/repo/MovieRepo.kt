package org.real.flickfusion.repo

import kotlinx.coroutines.flow.Flow
import org.real.flickfusion.model.Credits
import org.real.flickfusion.model.Movie
import org.real.flickfusion.model.MovieDetail

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: Gateway for movie
 */
interface MovieRepo {
    fun getTrendingMovies(): Flow<Result<List<Movie>>>
    fun getTopRatedMovies(page: Int): Flow<Result<List<Movie>>>
    fun getPopularMovies(page: Int): Flow<Result<List<Movie>>>
    fun getNowPlayingMovies(page: Int): Flow<Result<List<Movie>>>
    fun getUpcomingMovies(page: Int): Flow<Result<List<Movie>>>
    suspend fun getMovieDetail(movieId: Int): MovieDetail
    suspend fun getMovieCredits(movieId: Int): Credits
}