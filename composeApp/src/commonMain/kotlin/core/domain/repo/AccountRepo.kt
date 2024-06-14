package core.domain.repo

import core.domain.model.AccountInfo
import core.domain.model.Movie
import core.domain.model.TVShow
import core.services.local.AccountLocalSource
import core.services.remote.AccountGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description: account repository
 */
interface AccountRepo {
    fun getInfo(): Flow<Result<AccountInfo>>

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
        mediaType: String,
        mediaId: Int
    ): Flow<Result<Boolean>>

    fun getFavoriteMovie(page: Int): Flow<Result<List<Movie>>>

    fun getFavoriteTV(page: Int): Flow<Result<List<TVShow>>>
}

class AccountRepoImpl(
    private val accountGateway: AccountGateway,
    private val accountLocalSource: AccountLocalSource
) : AccountRepo {

    override fun getInfo(): Flow<Result<AccountInfo>> =
        accountLocalSource.getAccountId().flatMapConcat { accountId ->
            accountGateway.getInfo(accountId)
        }.map {
            it.onSuccess { accountInfo ->
                accountLocalSource.saveAccountId("${accountInfo.id}")
            }
            it
        }

    override fun addFavorite(mediaType: String, mediaId: Int): Flow<Result<Boolean>> =
        accountLocalSource.getAccountId()
            .zip(accountLocalSource.getSession()) { accountId, sessionId ->
                Pair(accountId, sessionId)
            }.flatMapConcat {
                accountGateway.addFavorite(it.first, it.second, mediaType, mediaId)
            }


    override fun getFavoriteMovie(page: Int): Flow<Result<List<Movie>>> =
        accountLocalSource.getAccountId().flatMapConcat {
            accountGateway.getFavoriteMovie(it, page)
        }


    override fun getFavoriteTV(page: Int): Flow<Result<List<TVShow>>> =
        accountLocalSource.getAccountId().flatMapConcat {
            accountGateway.getFavoriteTV(it, page)
        }

}