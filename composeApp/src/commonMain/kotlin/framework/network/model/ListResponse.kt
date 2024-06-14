package framework.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @created 24/05/2024
 * Description: Result list wrapper
 */
@Serializable
data class ListResponse<T>(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<T>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)