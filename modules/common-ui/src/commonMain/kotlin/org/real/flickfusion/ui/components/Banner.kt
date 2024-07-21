package org.real.flickfusion.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.real.flickfusion.ui.DisplayItem

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: Banner for media items
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaBanner(
    items: List<DisplayItem>,
    onItemClick: (String) -> Unit = {},
    scrollInterval: Long = 3500L
) {
    val pagerState = rememberPagerState { items.size }
    LaunchedEffect(pagerState) {
        while (true) {
            delay(scrollInterval)
            with(pagerState) {
                animateScrollToPage(
                    page = (currentPage + 1) % items.size
                )
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            SimpleBackDropDisplay(
                modifier = Modifier.fillMaxWidth(),
                item = items[page]
            ) {
                onItemClick(it)
            }
        }
    }
}

@Preview()
@Composable
fun MediaBannerPreview() {
    val items = listOf(
        DisplayItem.Movie(
            "1",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
        ),
        DisplayItem.Movie(
            "2",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
        ),
        DisplayItem.Movie(
            "3",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
        ),
        DisplayItem.Movie(
            "4",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
        ),
        DisplayItem.Movie(
            "5",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
            "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
        )
    )
    MediaBanner(items = items)
}