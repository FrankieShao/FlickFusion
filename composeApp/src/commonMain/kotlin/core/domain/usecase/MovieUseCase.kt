package core.domain.usecase

import core.domain.model.Movie
import core.services.remote.MovieGateway
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: movie use case
 */

/**
 * Get top rated movies
 */
class MovieTopRatedUseCase(
    private val movieRepo: MovieGateway,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getTopRatedMovies(page)
    }
}

/**
 *  Get trending movies

 */
class MovieTrendingUseCase(
    private val movieRepo: MovieGateway,
) {

    operator fun invoke(): Flow<Result<List<Movie>>> {
        return movieRepo.getTrendingMovies()
    }
}

/**
 * Get now playing movies
 */
class MovieNowPlayingUseCase(
    private val movieRepo: MovieGateway,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getNowPlayingMovies(page)
    }
}

/**
 * Get popular movies
 */
class MoviePopularUseCase(
    private val movieRepo: MovieGateway,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getPopularMovies(page)
    }
}

/**
 * Get upcoming movies
 */
class MovieUpcomingUseCase(
    private val movieRepo: MovieGateway,
) : MediaPageUseCase<Movie> {

    override operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return movieRepo.getUpcomingMovies(page)
    }
}