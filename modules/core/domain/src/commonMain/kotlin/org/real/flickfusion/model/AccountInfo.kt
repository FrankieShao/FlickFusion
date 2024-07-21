package org.real.flickfusion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: This file contains the model for the account information.
 */

@Serializable
data class AccountInfo(
    @SerialName("avatar")
    val avatar: Avatar,
    @SerialName("id")
    val id: Int,
    @SerialName("include_adult")
    val includeAdult: Boolean,
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String
) {
    @Serializable
    data class Avatar(
        @SerialName("gravatar")
        val gravatar: Gravatar,
        @SerialName("tmdb")
        val tmdb: Tmdb
    ) {
        @Serializable
        data class Gravatar(
            @SerialName("hash")
            val hash: String
        )

        @Serializable
        data class Tmdb(
            @SerialName("avatar_path")
            val avatarPath: String? = null
        )
    }
}
