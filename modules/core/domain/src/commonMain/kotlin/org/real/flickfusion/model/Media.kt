package org.real.flickfusion.model

import kotlinx.serialization.Serializable


/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: common media model for movie and tv.
 *  used to display media details in the UI
 */

interface Media

data class MediaModel(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val voteAverage: Double,
    val releaseDate: String,
    val type: MediaType,
    val overview: String,
    val backdropPath: String,
    val country: String, // from detail api origin_country
    val genres: List<String>, // from detail api genres
    val cast: List<Credits.Cast> // from credit api cast
) : Media {
    fun getGenres(): String {
        return genres.take(3).joinToString(separator = " · ")
    }

    fun getCast(): String {
        return cast.take(3).joinToString(separator = " · ") { it.name }
    }
}

@Serializable
enum class MediaType(val value: String) {
    MOVIE("Movie"),
    TV("TV Shows");

    companion object {
        fun fromValue(value: String): MediaType? {
            return entries.firstOrNull { it.value == value }
        }
    }
}