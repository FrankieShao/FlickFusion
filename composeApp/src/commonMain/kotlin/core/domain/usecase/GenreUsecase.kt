package core.domain.usecase

import core.services.remote.GenreGateway
import kotlinx.coroutines.flow.combine

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: This file contains the use case for getting genres.
 */
class GenreUseCase(
    private val gateWay: GenreGateway,
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