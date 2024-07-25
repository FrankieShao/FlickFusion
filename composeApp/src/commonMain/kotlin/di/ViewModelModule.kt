package di

import feature.main.movie.vm.MovieFeedViewModel
import feature.main.tv.vm.TVFeedViewModel
import profile.MineViewModel
import favorite.FavoriteMainViewModel
import favorite.IFavoriteMainViewModel
import feature.main.movie.vm.IMovieFeedViewModel
import feature.main.tv.vm.ITVFeedViewModel
import search.GenresViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: ViewModel koin module, responsible for viewmodel related dependencies
 */
val ViewModelModule = module {
    singleOf<IMovieFeedViewModel>(::MovieFeedViewModel)
    singleOf<ITVFeedViewModel>(::TVFeedViewModel)
    singleOf(::MineViewModel)
    singleOf<IFavoriteMainViewModel>(::FavoriteMainViewModel)
    singleOf(::GenresViewModel)
}