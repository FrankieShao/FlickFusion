package di

import org.koin.dsl.module
import org.real.flickfusion.usecase.AddFavoriteUseCase
import org.real.flickfusion.usecase.FavoriteMovieUseCase
import org.real.flickfusion.usecase.FavoriteTvUseCase
import org.real.flickfusion.usecase.GenreUseCase
import org.real.flickfusion.usecase.GetInfoUseCase
import org.real.flickfusion.usecase.MediaDetailUseCase
import org.real.flickfusion.usecase.MovieNowPlayingUseCase
import org.real.flickfusion.usecase.MoviePopularUseCase
import org.real.flickfusion.usecase.MovieTopRatedUseCase
import org.real.flickfusion.usecase.MovieTrendingUseCase
import org.real.flickfusion.usecase.MovieUpcomingUseCase
import org.real.flickfusion.usecase.SearchUseCase
import org.real.flickfusion.usecase.TVAiringTodayUseCase
import org.real.flickfusion.usecase.TVOnTheAirUseCase
import org.real.flickfusion.usecase.TVPopularUseCase
import org.real.flickfusion.usecase.TVTopRatedUseCase
import org.real.flickfusion.usecase.TVTrendingUseCase

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