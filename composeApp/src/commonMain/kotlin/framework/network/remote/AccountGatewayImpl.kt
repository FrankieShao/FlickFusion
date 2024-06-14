package framework.network.remote

import com.real.network.API
import framework.network.get
import framework.network.getList
import framework.network.post
import core.domain.model.AccountInfo
import core.domain.model.Movie
import core.domain.model.TVShow
import core.services.remote.AccountGateway
import framework.network.UrlFormatter
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description:  Gateway for Mine
 */
class AccountGatewayImpl constructor(
    private val client: HttpClient
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
        post(
            client = client,
            url = UrlFormatter(API.ACCOUNT.FAVORITE, accountId),
            param = mapOf("session_id" to sessionId),
            body = mapOf("media_type" to mediaType, "media_id" to mediaId, "favorite" to true)
        )

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

