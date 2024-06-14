package core.domain.usecase

import core.services.remote.SearchGateway

/**
 * @author Frank Shao
 * @created 24/05/2024
 * Description: todo not implemented
 */
class SearchUseCase(
    private val resource: SearchGateway
) {
    operator fun invoke(query: String) = resource.search(query)
}