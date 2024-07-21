package org.real.flickfusion.repo

import org.real.flickfusion.remote.MovieGateway
/**
 * @author Frank Shao
 * @created 20/07/2024
 * Description:
 */
class MovieRepoImpl(private val movieGateway: MovieGateway) : MovieRepo {
    override fun getTrendingMovies() = movieGateway.getTrendingMovies()
    override fun getTopRatedMovies(page: Int) = movieGateway.getTopRatedMovies(page)
    override fun getPopularMovies(page: Int) = movieGateway.getPopularMovies(page)
    override fun getNowPlayingMovies(page: Int) = movieGateway.getNowPlayingMovies(page)
    override fun getUpcomingMovies(page: Int) = movieGateway.getUpcomingMovies(page)
    override suspend fun getMovieDetail(movieId: Int) = movieGateway.getMovieDetail(movieId)
    override suspend fun getMovieCredits(movieId: Int) = movieGateway.getMovieCredits(movieId)
}