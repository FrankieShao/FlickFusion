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
import core.domain.model.MediaType
import feature.main.movie.ui.MoviePage
import feature.main.tv.ui.TVPage
import kotlinx.coroutines.launch
import ui.components.PageIndicator

/**
 * @author Frank Shao
 * @created 11/06/2024
 * Description: Home Main Screen entrypoint
 */

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeMainScreen(
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
                    onItemClick = { id ->
                        toMediaDetail(id, MediaType.TV.value)
                    }, onEnterList = { category ->
                        toCategory(category, MediaType.TV.value)
                    })

                else -> MoviePage(
                    onItemClick = { id ->
                        toMediaDetail(id, MediaType.MOVIE.value)
                    }, onEnterList = { category ->
                        toCategory(category, MediaType.MOVIE.value)
                    })
            }
        }
    }
}
