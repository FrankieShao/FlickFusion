package core.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: This file contains the model for the configuration.
 * mainly for image url prefix
 */
@Serializable
data class Configure(
    @SerialName("images")
    val images: Images
) {
    @Serializable
    data class Images(
        @SerialName("backdrop_sizes")
        val backdropSizes: List<String>,
        @SerialName("base_url")
        val baseUrl: String,
        @SerialName("logo_sizes")
        val logoSizes: List<String>,
        @SerialName("poster_sizes")
        val posterSizes: List<String>,
        @SerialName("profile_sizes")
        val profileSizes: List<String>,
        @SerialName("secure_base_url")
        val secureBaseUrl: String,
        @SerialName("still_sizes")
        val stillSizes: List<String>
    )

    val urlPrefix = images.secureBaseUrl + images.posterSizes.elementAtOrNull(4)
}