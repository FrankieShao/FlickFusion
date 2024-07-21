package feature.main.list

import org.real.flickfusion.model.Media
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.model.MovieCategory
import org.real.flickfusion.model.TVCategory
import org.real.flickfusion.repo.MovieRepo
import org.real.flickfusion.repo.TVRepo
import org.real.flickfusion.usecase.MediaPageUseCase
import org.real.flickfusion.usecase.MovieNowPlayingUseCase
import org.real.flickfusion.usecase.MoviePopularUseCase
import org.real.flickfusion.usecase.MovieTopRatedUseCase
import org.real.flickfusion.usecase.MovieUpcomingUseCase
import org.real.flickfusion.usecase.TVAiringTodayUseCase
import org.real.flickfusion.usecase.TVOnTheAirUseCase
import org.real.flickfusion.usecase.TVPopularUseCase
import org.real.flickfusion.usecase.TVTopRatedUseCase

/**
 * @author Frank Shao
 * @created 13/06/2024
 * Description: Category Use Case Mapper
 */
val findUseCase: (
    category: String,
    mediaType: String,
    movieRepo: MovieRepo,
    tvRepo: TVRepo
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