package core.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: This file contains the model for the credits.
 */
@Serializable
data class Credits(
    @SerialName("cast")
    val cast: List<Cast>,
    @SerialName("crew")
    val crew: List<Crew>,
    @SerialName("id")
    val id: Int
) {
    @Serializable
    data class Cast(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("cast_id")
        val castId: Int?= null,
        @SerialName("character")
        val character: String,
        @SerialName("credit_id")
        val creditId: String,
        @SerialName("gender")
        val gender: Int,
        @SerialName("id")
        val id: Int,
        @SerialName("known_for_department")
        val knownForDepartment: String,
        @SerialName("name")
        val name: String,
        @SerialName("order")
        val order: Int,
        @SerialName("original_name")
        val originalName: String,
        @SerialName("popularity")
        val popularity: Double,
        @SerialName("profile_path")
        val profilePath: String? = null
    )

    @Serializable
    data class Crew(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("credit_id")
        val creditId: String,
        @SerialName("department")
        val department: String,
        @SerialName("gender")
        val gender: Int,
        @SerialName("id")
        val id: Int,
        @SerialName("job")
        val job: String,
        @SerialName("known_for_department")
        val knownForDepartment: String,
        @SerialName("name")
        val name: String,
        @SerialName("original_name")
        val originalName: String,
        @SerialName("popularity")
        val popularity: Double,
        @SerialName("profile_path")
        val profilePath: String? = null
    )
}