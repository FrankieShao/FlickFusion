package org.real.flickfusion.usecase

import org.real.flickfusion.repo.SearchRepo

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: todo not implemented
 */
class SearchUseCase(
    private val resource: SearchRepo
) {
    operator fun invoke(query: String) = resource.search(query)
}