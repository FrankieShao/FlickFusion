package feature.main.list

import core.domain.model.Media
import core.domain.model.MediaType
import core.domain.model.MovieCategory
import core.domain.model.TVCategory
import core.domain.usecase.MediaPageUseCase
import core.domain.usecase.MovieNowPlayingUseCase
import core.domain.usecase.MoviePopularUseCase
import core.domain.usecase.MovieTopRatedUseCase
import core.domain.usecase.MovieUpcomingUseCase
import core.domain.usecase.TVAiringTodayUseCase
import core.domain.usecase.TVOnTheAirUseCase
import core.domain.usecase.TVPopularUseCase
import core.domain.usecase.TVTopRatedUseCase
import core.services.remote.MovieGateway
import core.services.remote.TVGateway

/**
 * @author Frank Shao
 * @created 13/06/2024
 * Description: Category Use Case Mapper
 */
val findUseCase: (
    category: String,
    mediaType: String,
    movieRepo: MovieGateway,
    tvRepo: TVGateway
) -> MediaPageUseCase<out Media> =
    { category, mediaType, movieRepo, tvRepo ->
        when (mediaType) {
            MediaType.TV.value -> {
                when (category) {
                    TVCategory.Popular -> {
                        TVPopularUseCase(tvRepo)
                    }

                    TVCategory.TopRated -> {
                        TVTopRatedUseCase(tvRepo)
                    }

                    TVCategory.OnTheAir -> {
                        TVOnTheAirUseCase(tvRepo)
                    }

                    TVCategory.AiringToday -> {
                        TVAiringTodayUseCase(tvRepo)
                    }

                    else -> throw IllegalArgumentException("Invalid category")
                }
            }

            MediaType.MOVIE.value -> {
                when (category) {
                    MovieCategory.Popular -> {
                        MoviePopularUseCase(movieRepo)
                    }

                    MovieCategory.TopRated -> {
                        MovieTopRatedUseCase(movieRepo)
                    }

                    MovieCategory.Upcoming -> {
                        MovieUpcomingUseCase(movieRepo)
                    }

                    MovieCategory.NowPlaying -> {
                        MovieNowPlayingUseCase(movieRepo)
                    }

                    else -> throw IllegalArgumentException("Invalid category")
                }
            }

            else -> throw IllegalArgumentException("Invalid media type")
        }
    }