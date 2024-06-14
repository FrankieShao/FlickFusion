package core.services.remote

import core.domain.model.SearchResult
import kotlinx.coroutines.flow.Flow

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description:     Gateway for search
 */
interface SearchGateway {
     fun search(key: String): Flow<Result<List<SearchResult>>>
}