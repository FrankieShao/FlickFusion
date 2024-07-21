package org.real.flickfusion.usecase

import org.real.flickfusion.model.Movie
import kotlinx.coroutines.flow.Flow
import org.real.flickfusion.repo.MovieRepo

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: movie use case
 */

/**
 * Get top rated movies
 */
class MovieTopRatedUseCase(
    private val movieRepo: MovieRepo,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getTopRatedMovies(page)
    }
}

/**
 *  Get trending movies

 */
class MovieTrendingUseCase(
    private val movieRepo: MovieRepo,
) {

    operator fun invoke(): Flow<Result<List<Movie>>> {
        return movieRepo.getTrendingMovies()
    }
}

/**
 * Get now playing movies
 */
class MovieNowPlayingUseCase(
    private val movieRepo: MovieRepo,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getNowPlayingMovies(page)
    }
}

/**
 * Get popular movies
 */
class MoviePopularUseCase(
    private val movieRepo: MovieRepo,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getPopularMovies(page)
    }
}

/**
 * Get upcoming movies
 */
class MovieUpcomingUseCase(
    private val movieRepo: MovieRepo,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getUpcomingMovies(page)
    }
}