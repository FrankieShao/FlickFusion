package org.real.flickfusion.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.IHttp
import org.real.flickfusion.API
import org.real.flickfusion.UrlFormatter
import org.real.flickfusion.model.AccountInfo
import org.real.flickfusion.model.ConfirmResponse
import org.real.flickfusion.model.Movie
import org.real.flickfusion.model.TVShow

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description:  Gateway for Mine
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

class AccountGatewayImpl constructor(
    private val client: IHttp
) : AccountGateway {

    override fun getInfo(accountId: String): Flow<Result<AccountInfo>> =
        get(
            client = client,
            url = API.ACCOUNT.ACCOUNT + accountId
        )

    override fun addFavorite(
        accountId: String,
        sessionId: String,
        mediaType: String,
        mediaId: Int
    ): Flow<Result<Boolean>> =
        post<ConfirmResponse>(
            client = client,
            url = UrlFormatter(API.ACCOUNT.FAVORITE, accountId),
            param = mapOf("session_id" to sessionId),
            body = mapOf("media_type" to mediaType, "media_id" to mediaId, "favorite" to true)
        ).map { result ->
            result.map { it.success }
        }

    override fun getFavoriteMovie(
        accountId: String,
        page: Int
    ): Flow<Result<List<Movie>>> =
        getList(
            client = client,
            url = UrlFormatter(API.ACCOUNT.FAVORITE_MOVIE, accountId),
            param = mapOf("page" to page.toString())
        )

    override fun getFavoriteTV(
        accountId: String,
        page: Int
    ): Flow<Result<List<TVShow>>> =
        getList(
            client = client,
            url = UrlFormatter(API.ACCOUNT.FAVORITE_TV, accountId),
            param = mapOf("page" to page.toString())
        )
}


