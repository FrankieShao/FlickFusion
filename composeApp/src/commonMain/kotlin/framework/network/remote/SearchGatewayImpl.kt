package framework.network.remote

import com.real.network.API
import framework.network.getList
import core.domain.model.SearchResult
import core.services.remote.SearchGateway
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description:
 * todo there is multiple result entity, need to be fixed
 */
class SearchGatewayImpl    constructor(
    private val client: HttpClient
) : SearchGateway {

    override  fun search(key: String): Flow<Result<List<SearchResult>>> =
        getList(client, API.SEARCH.SEARCH, mapOf("query" to key))
}