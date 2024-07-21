package org.real.flickfusion.repo

import kotlinx.coroutines.flow.Flow
import org.real.flickfusion.model.SearchResult

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description:     Gateway for search
 */
interface SearchRepo {
     fun search(key: String): Flow<Result<List<SearchResult>>>
}