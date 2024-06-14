package core.services.remote

import core.domain.model.AccountInfo
import core.domain.model.Movie
import core.domain.model.TVShow
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: Account gateway
 */
interface AccountGateway {

    fun getInfo(accountId: String): Flow<Result<AccountInfo>>

    /***
     * Add favorite
     * @param account_id
     * {
     *     "media_type": "movie",
     *     "media_id": 550,
     *     "favorite": true
     * }
     */
    fun addFavorite(
        accountId: String,
        sessionId: String,
        mediaType: String,
        mediaId: Int
    ): Flow<Result<Boolean>>

    fun getFavoriteMovie(accountId: String, page: Int): Flow<Result<List<Movie>>>

    fun getFavoriteTV(accountId: String, page: Int): Flow<Result<List<TVShow>>>
}