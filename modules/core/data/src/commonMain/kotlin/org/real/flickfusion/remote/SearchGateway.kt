package org.real.flickfusion.remote

import kotlinx.coroutines.flow.Flow
import net.IHttp
import org.real.flickfusion.API
import org.real.flickfusion.model.SearchResult
/**
 * @author Frank Shao
 * @created 26/05/2024
 * Description:
 * todo there is multiple result entity, need to be fixed
 */
interface SearchGateway {
    fun search(key: String): Flow<Result<List<SearchResult>>>
}

class SearchGatewayImpl  constructor(
    private val client: IHttp
) : SearchGateway {

    override  fun search(key: String): Flow<Result<List<SearchResult>>> =
        getList(client, API.SEARCH.SEARCH, mapOf("query" to key))
}