package org.real.flickfusion.usecase

import org.real.flickfusion.model.TVShow
import kotlinx.coroutines.flow.Flow
import org.real.flickfusion.repo.TVRepo

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: Use cases for TV shows
 */

/**
 * Get trending tv shows
 */
class TVTrendingUseCase(
    private val tvRepo: TVRepo,
) {

    operator fun invoke(): Flow<Result<List<TVShow>>> {
        return tvRepo.getTrendingTVShows()
    }
}

/**
 * Get top rated tv shows
 */
class TVTopRatedUseCase(
    private val tvRepo: TVRepo,
) : MediaPageUseCase<TVShow> {

    override operator fun invoke(page: Int): Flow<Result<List<TVShow>>> {
        return tvRepo.getTopRatedTVShows(page)
    }
}

/**
 * Get on the air tv shows
 */
class TVOnTheAirUseCase(
    private val tvRepo: TVRepo,
) : MediaPageUseCase<TVShow> {

    override operator fun invoke(page: Int): Flow<Result<List<TVShow>>> {
        return tvRepo.getOnTheAirTVShows(page)
    }
}

/**
 * Get popular tv shows
 */
class TVPopularUseCase(
    private val tvRepo: TVRepo,
) : MediaPageUseCase<TVShow> {

    override operator fun invoke(page: Int): Flow<Result<List<TVShow>>> {
        return tvRepo.getPopularTVShows(page)
    }
}

/**
 * Get tv shows airing today
 */
class TVAiringTodayUseCase(
    private val tvRepo: TVRepo,
) : MediaPageUseCase<TVShow> {

    override operator fun invoke(page: Int): Flow<Result<List<TVShow>>> {
        return tvRepo.getAiringTodayTVShows(page)
    }
}