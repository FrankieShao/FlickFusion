package core.domain.usecase

import core.domain.model.AccountInfo
import core.domain.model.Movie
import core.domain.model.TVShow
import core.domain.repo.AccountRepo
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description:
 */

/**
 * Get account info
 */
class GetInfoUseCase(
    private val accountRepo: AccountRepo
) {
    operator fun invoke(): Flow<Result<AccountInfo>> {
        return accountRepo.getInfo()
    }
}

/**
 * Add favorite tv or movie
 */
class AddFavoriteUseCase(
    private val accountRepo: AccountRepo
) {
    operator fun invoke(media_type: String, media_id: Int): Flow<Result<Boolean>> {
        return accountRepo.addFavorite(media_type, media_id)
    }
}

/**
 * Get favorite movie
 */
class FavoriteMovieUseCase(
    private val accountRepo: AccountRepo
) {
    operator fun invoke(page: Int): Flow<Result<List<Movie>>> {
        return accountRepo.getFavoriteMovie(page)
    }
}

/**
 * Get favorite tv
 */
class FavoriteTvUseCase(
    private val accountRepo: AccountRepo
) {
    operator fun invoke(page: Int): Flow<Result<List<TVShow>>> {
        return accountRepo.getFavoriteTV(page)
    }
}