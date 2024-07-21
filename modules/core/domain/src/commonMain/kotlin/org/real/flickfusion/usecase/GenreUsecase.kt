package org.real.flickfusion.usecase

import kotlinx.coroutines.flow.combine
import org.real.flickfusion.repo.GenreRepo

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: This file contains the use case for getting genres.
 */
class GenreUseCase(
    private val gateWay: GenreRepo,
) {
    operator fun invoke() =
        combine(gateWay.getMovieGenres(), gateWay.getTVGenres()) { movieGenres, tvGenres ->
            if (movieGenres.isSuccess && tvGenres.isSuccess) {
                Result.success(movieGenres.getOrNull()!! + tvGenres.getOrNull()!!)
            } else {
                Result.failure(Exception("Failed to get genres"))
            }
        }
}