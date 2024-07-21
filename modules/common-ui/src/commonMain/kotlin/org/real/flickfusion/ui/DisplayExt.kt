package org.real.flickfusion.ui

import org.real.flickfusion.model.Configure
import org.real.flickfusion.model.Media
import org.real.flickfusion.model.Movie
import org.real.flickfusion.model.TVShow

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: Display Item Extension
 */

fun Movie.toDisplayItem(configure: Configure?) = DisplayItem.Movie(
    movieId = id.toString(),
    posterUrl = configure?.urlPrefix + posterPath,
    backdropUrl = configure?.urlPrefix + backdropPath,
)

fun TVShow.toDisplayItem(configure: Configure?) = DisplayItem.Movie(
    movieId = id.toString(),
    posterUrl = configure?.urlPrefix + posterPath,
    backdropUrl = configure?.urlPrefix + backdropPath,
)

fun Media.toDisplayItem(configure: Configure?) = when (this) {
    is Movie -> toDisplayItem(configure)
    is TVShow -> toDisplayItem(configure)
    else -> throw IllegalArgumentException("Unknown media type")
}
