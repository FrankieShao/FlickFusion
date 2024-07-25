package feature.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.main.movie.ui.MoviePage
import feature.main.movie.vm.IMovieFeedViewModel
import feature.main.tv.ui.TVPage
import feature.main.tv.vm.ITVFeedViewModel
import kotlinx.coroutines.launch
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.ui.components.PageIndicator

/**
 * @author Frank Shao
 * @created 11/06/2024
 * Description: Home Main Screen entrypoint
 */

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeMainScreen(
    iMovieFeedViewModel: IMovieFeedViewModel,
    iTVFeedViewModel: ITVFeedViewModel,
    toMediaDetail: (String, String) -> Unit,
    toCategory: (String, String) -> Unit
) {

    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageIndicator(
            modifier = Modifier
                .width(300.dp),
            selectedIndex = pagerState.currentPage,
            items = listOf("Movies", "TV"),
            onSelectionChange = { index ->
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }
        )
        HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = 1
        ) {
            when (it) {
                1 -> TVPage(
                    viewModel = iTVFeedViewModel,
                    onItemClick = { id ->
                        toMediaDetail(id, MediaType.TV.value)
                    }, onEnterList = { category ->
                        toCategory(category, MediaType.TV.value)
                    })

                else -> MoviePage(
                    viewModel = iMovieFeedViewModel,
                    onItemClick = { id ->
                        toMediaDetail(id, MediaType.MOVIE.value)
                    }, onEnterList = { category ->
                        toCategory(category, MediaType.MOVIE.value)
                    })
            }
        }
    }
}
