package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import detail.MediaDetailScreen
import detail.MediaDetailViewModel
import favorite.FavoriteMainScreen
import favorite.IFavoriteMainViewModel
import feature.main.HomeMainScreen
import feature.main.list.CategoryList
import feature.main.list.CategoryListViewModel
import feature.main.movie.vm.IMovieFeedViewModel
import feature.main.tv.vm.ITVFeedViewModel
import org.koin.compose.koinInject
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.util.collectAsStateWithLifecycle
import profile.MineScreen
import profile.MineViewModel
import search.SearchMainScreen

/**
 * @author Frank Shao
 * @created 14/06/2024
 * Description: Voyager Screens
 */

// HomeMainScreen, containing: MovieFeed, TvFeed
class HomeMainScreen : Screen {

    override val key = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModelMovie: IMovieFeedViewModel = koinInject()
        val viewModelTV: ITVFeedViewModel = koinInject()
        HomeMainScreen(
            iMovieFeedViewModel = viewModelMovie,
            iTVFeedViewModel = viewModelTV,
            toMediaDetail = { id, type ->
                navigator.push(MediaDetailScreen(id, type))
            }, toCategory = { category, mediaType ->
                navigator.push(CategoryListScreen(category, mediaType))
            })
    }
}

// MediaDetailScreen, Movie, TV detail page
data class MediaDetailScreen(
    val mediaId: String,
    val mediaType: String
) : Screen {
    override val key = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val factory = remember {
            MediaDetailViewModel.provideFactory(MediaType.fromValue(mediaType)!!, mediaId.toInt())
        }
        val viewModel: MediaDetailViewModel = viewModel(factory = factory)
        MediaDetailScreen(viewModel) {
            navigator.pop()
        }
    }
}

// CategoryListScreen, Movie, TV category list
data class CategoryListScreen(
    val category: String,
    val mediaType: String
) : Screen {
    override val key = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val factory = remember {
            CategoryListViewModel.provideFactory(category, mediaType)
        }
        val viewModel: CategoryListViewModel = viewModel(factory = factory)
        CategoryList(
            category = category,
            mediaType = mediaType,
            viewModel = viewModel,
            onItemClicked = {
                navigator.push(MediaDetailScreen(it.id, mediaType))
            },
            onNavBack = {
                navigator.pop()
            }
        )
    }
}

// SearchScreen, search page
class SearchScreen : Screen {

    override val key = uniqueScreenKey

    @Composable
    override fun Content() {
        SearchMainScreen()
    }
}

// FavoriteScreen, favorite page
class FavoriteScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: IFavoriteMainViewModel = koinInject()
        FavoriteMainScreen(viewModel) {
            navigator.push(MediaDetailScreen(it.id, it.type.value))
            navigator.push(MediaDetailScreen(it.id.toString(), it.type.value))
        }
    }
}

// MineScreen, mine page
class MineScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: MineViewModel = koinInject()
        val uiState by viewModel.state.collectAsStateWithLifecycle()
        MineScreen(uiState = uiState)
    }
}