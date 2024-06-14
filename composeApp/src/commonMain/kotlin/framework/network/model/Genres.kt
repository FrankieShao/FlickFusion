package framework.network.model

import core.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Genres response
 */
@Serializable
data class Genres(
    @SerialName("genres")
    val genres: List<Genre>
)