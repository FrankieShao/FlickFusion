import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import di.NetworkModule
import di.PersistModule
import di.RepoModule
import di.UseCaseModule
import di.ViewModelModule
import navigation.BottomNavigationBar
import navigation.HomeTab
import navigation.TABS
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import ui.theme.AppTheme
import ui.theme.ALL_BACKGROUND_GRADIENT
import ui.theme.PART_BACKGROUND_GRADIENT

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    KoinApplication(application = {
        // todo all logger
        modules(NetworkModule, PersistModule, UseCaseModule, RepoModule, ViewModelModule)
    }) {
        AppTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = ALL_BACKGROUND_GRADIENT)
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                MainScreen()
            }
        }
    }
    /**
     * Set the singleton image loader factory for coil3
     */
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
}

// entry point
@Composable
fun MainScreen() {
    // voyager tab navigator
    TabNavigator(
        HomeTab,
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = TABS
            )
        }
    ) { _ ->
        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBar(
                    modifier = Modifier
                )
            }
        ) {
            Box(
                modifier = Modifier.padding(it)
                    .fillMaxSize()
                    .background(brush = PART_BACKGROUND_GRADIENT)
            ) {
                CurrentTab()
            }
        }
    }
}


/**
 * Get the async image loader of coil3
 */
fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).crossfade(true).logger(DebugLogger()).build()