package ui

import core.domain.model.MediaType

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: unified display item, display poster
 */

sealed class DisplayItem(
    val id: String,
    open val posterUrl: String,
    open val backdropUrl: String,
    val type: MediaType = MediaType.MOVIE
) {
    data class Movie(
        val movieId: String,
        override val posterUrl: String,
        override val backdropUrl: String
    ) : DisplayItem(movieId, posterUrl, backdropUrl)

    data class TV(
        val tvId: String,
        override val posterUrl: String,
        override val backdropUrl: String
    ) : DisplayItem(tvId, posterUrl, backdropUrl, MediaType.TV)
}