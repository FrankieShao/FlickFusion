package org.real.flickfusion.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import org.real.flickfusion.local.AccountLocalSource
import org.real.flickfusion.model.AccountInfo
import org.real.flickfusion.model.Movie
import org.real.flickfusion.model.TVShow
import org.real.flickfusion.remote.AccountGateway

/**
 * @author Frank Shao
 * @created 13/07/2024
 * Description: account repository
 */
@ExperimentalCoroutinesApi
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