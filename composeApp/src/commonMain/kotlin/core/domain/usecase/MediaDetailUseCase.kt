package core.domain.usecase

import core.domain.model.MediaModel
import core.domain.model.MediaType
import core.domain.repo.ConfigureRepo
import core.services.remote.MovieGateway
import core.services.remote.TVGateway

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: This file contains the use case for getting media detail.
 */
class MediaDetailUseCase(
    private val movieGateway: MovieGateway,
    private val tvGateway: TVGateway,
    private val configureRepo: ConfigureRepo
) {
    suspend operator fun invoke(mediaType: MediaType, mediaId: Int): MediaModel {
        val configure = configureRepo.get()
        when (mediaType) {
            MediaType.MOVIE -> {
                // Get movie detail
                val movieDetail = movieGateway.getMovieDetail(mediaId)
                // Get movie credits, mainly for cast
                val credits = movieGateway.getMovieCredits(mediaId)
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
                val tvDetail = tvGateway.getTVShowDetail(mediaId)
                val credits = tvGateway.getTVShowCredits(mediaId)
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