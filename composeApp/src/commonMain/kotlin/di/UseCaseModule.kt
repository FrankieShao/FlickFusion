package di

import core.domain.usecase.AddFavoriteUseCase
import core.domain.usecase.FavoriteMovieUseCase
import core.domain.usecase.FavoriteTvUseCase
import core.domain.usecase.GenreUseCase
import core.domain.usecase.GetInfoUseCase
import core.domain.usecase.MediaDetailUseCase
import core.domain.usecase.MovieNowPlayingUseCase
import core.domain.usecase.MoviePopularUseCase
import core.domain.usecase.MovieTopRatedUseCase
import core.domain.usecase.MovieTrendingUseCase
import core.domain.usecase.MovieUpcomingUseCase
import core.domain.usecase.SearchUseCase
import core.domain.usecase.TVAiringTodayUseCase
import core.domain.usecase.TVOnTheAirUseCase
import core.domain.usecase.TVPopularUseCase
import core.domain.usecase.TVTopRatedUseCase
import core.domain.usecase.TVTrendingUseCase
import org.koin.dsl.module

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: UseCase koin module, responsible for usecase related dependencies
 */
val AccountUseCaseModule = module {
    single { GetInfoUseCase(get()) }
    single { AddFavoriteUseCase(get()) }
    single { FavoriteMovieUseCase(get()) }
    single { FavoriteTvUseCase(get()) }
}

val GenreUseCaseModule = module {
    single { GenreUseCase(get()) }
}

val MediaDetailUseCaseModule = module {
    single { MediaDetailUseCase(get(), get(), get()) }
}

val MovieUseCaseModule = module {
    single { MovieTopRatedUseCase(get()) }
    single { MovieTrendingUseCase(get()) }
    single { MovieNowPlayingUseCase(get()) }
    single { MoviePopularUseCase(get()) }
    single { MovieUpcomingUseCase(get()) }
}

val TVUseCaseModule = module {
    single { TVTrendingUseCase(get()) }
    single { TVTopRatedUseCase(get()) }
    single { TVOnTheAirUseCase(get()) }
    single { TVPopularUseCase(get()) }
    single { TVAiringTodayUseCase(get()) }
}
val SearchUseCaseModule = module {
    single { SearchUseCase(get()) }
}

val UseCaseModule = module {
    includes(
        AccountUseCaseModule,
        GenreUseCaseModule,
        MediaDetailUseCaseModule,
        MovieUseCaseModule,
        TVUseCaseModule,
        SearchUseCaseModule
    )
}