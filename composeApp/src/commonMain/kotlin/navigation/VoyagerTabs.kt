package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import flickfusion.composeapp.generated.resources.Res
import flickfusion.composeapp.generated.resources.ic_account
import flickfusion.composeapp.generated.resources.ic_favorite
import flickfusion.composeapp.generated.resources.ic_home
import flickfusion.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.vectorResource

/**
 * @author Frank Shao
 * @created 12/06/2024
 * Description: App Tabs, built with Voyager
 */

// Main screen tabs
val TABS = listOf(HomeTab, SearchTab, FavoriteTab, MineTab)

// HomeTab
object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(vectorResource(Res.drawable.ic_home))

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(HomeMainScreen())
    }
}
//SearchTab
object SearchTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(vectorResource(Res.drawable.ic_search))

            return remember {
                TabOptions(
                    index = 1u,
                    title = "Search",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(SearchScreen())
    }
}

// favoriteTab
object FavoriteTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(vectorResource(Res.drawable.ic_favorite))

            return remember {
                TabOptions(
                    index = 2u,
                    title = "favorite",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(FavoriteScreen())
    }
}


object MineTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(vectorResource(Res.drawable.ic_account))

            return remember {
                TabOptions(
                    index = 3u,
                    title = "mine",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(MineScreen())
    }
}

