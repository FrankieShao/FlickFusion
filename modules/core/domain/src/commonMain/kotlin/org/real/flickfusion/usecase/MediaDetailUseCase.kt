package org.real.flickfusion.usecase

import org.real.flickfusion.model.MediaModel
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.repo.MovieRepo
import org.real.flickfusion.repo.TVRepo

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: This file contains the use case for getting media detail.
 */
class MediaDetailUseCase(
    private val movieRepo: MovieRepo,
    private val tvRepo: TVRepo,
    private val configureRepo: ConfigureRepo
) {
    suspend operator fun invoke(mediaType: MediaType, mediaId: Int): MediaModel {
        val configure = configureRepo.get()
        when (mediaType) {
            MediaType.MOVIE -> {
                // Get movie detail
                val movieDetail = movieRepo.getMovieDetail(mediaId)
                // Get movie credits, mainly for cast
                val credits = movieRepo.getMovieCredits(mediaId)
                return MediaModel(
                    id = movieDetail.id,
                    title = movieDetail.title,
                    posterUrl = "${configure?.urlPrefix}${movieDetail.posterPath}",
                    overview = movieDetail.overview,
                    releaseDate = movieDetail.releaseDate,
                    voteAverage = movieDetail.voteAverage,
                    type = MediaType.MOVIE,
                    country = movieDetail.originCountry.firstOrNull() ?: "",
                    genres = movieDetail.genres.map { it.name },
                    cast = credits.cast.map {
                        it.copy(
                            profilePath = "${configure?.urlPrefix}${it.profilePath}"
                        )
                    },
                    backdropPath = "${configure?.urlPrefix}${movieDetail.backdropPath}"
                )
            }

            MediaType.TV -> {
                val tvDetail = tvRepo.getTVShowDetail(mediaId)
                val credits = tvRepo.getTVShowCredits(mediaId)
                return MediaModel(
                    id = tvDetail.id,
                    title = tvDetail.name,
                    posterUrl = "${configure?.urlPrefix}${tvDetail.posterPath}",
                    overview = tvDetail.overview,
                    releaseDate = tvDetail.firstAirDate,
                    voteAverage = tvDetail.voteAverage,
                    type = MediaType.TV,
                    country = tvDetail.originCountry.firstOrNull() ?: "",
                    genres = tvDetail.genres.map { it.name },
                    cast = credits.cast.map {
                        it.copy(
                            profilePath = "${configure?.urlPrefix}${it.profilePath}"
                        )
                    },
                    backdropPath = "${configure?.urlPrefix}${tvDetail.backdropPath}"
                )

            }
        }
    }
}