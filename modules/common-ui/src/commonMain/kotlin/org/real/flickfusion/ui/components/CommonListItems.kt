package org.real.flickfusion.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.real.flickfusion.ui.Constant.Default.IMAGE_HEIGHT
import org.real.flickfusion.ui.Constant.Default.IMAGE_WIDTH
import org.real.flickfusion.ui.DisplayItem

/**
 * @author Frank Shao
 * @created 02/06/2024
 * Description:
 */

/**
 * Media(Movie or TV) poster display
 */
@Composable
fun SimplePosterDisplay(
    modifier: Modifier = Modifier,
    item: DisplayItem,
    onItemClick: (String) -> Unit
) {
    AsyncImage(
        model = item.posterUrl,
        placeholder = ColorPainter(color = Color.Gray),
        contentDescription = "Description",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .shadow(3.dp, RoundedCornerShape(6.dp))
            .clickable { onItemClick(item.id) }
    )
}

/**
 * Media(Movie or TV) backdrop display
 */
@Composable
fun SimpleBackDropDisplay(
    modifier: Modifier = Modifier,
    item: DisplayItem,
    onItemClick: (String) -> Unit
) {
    AsyncImage(
        model = item.backdropUrl,
        placeholder = ColorPainter(color = Color.Gray),
        contentDescription = "Description",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .shadow(6.dp, RoundedCornerShape(6.dp))
            .clickable { onItemClick(item.id) }
    )
}

/**
 * Display row for media items
 */
@Composable
fun SimpleDisplayRow(
    modifier: Modifier = Modifier,
    items: List<DisplayItem>,
    onItemClick: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyRow(
        state = lazyListState,
        modifier = modifier.testTag("LazyRow"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id }
        ) { index ->
            SimplePosterDisplay(
                modifier = Modifier
                    .width(IMAGE_WIDTH)
                    .height(IMAGE_HEIGHT).testTag(items[index].id),
                item = items[index],
                onItemClick = onItemClick
            )
        }
    }
}

@Preview
@Composable
fun SimpleItemPreview() {
    SimplePosterDisplay(
        modifier = Modifier
            .width(IMAGE_WIDTH)
            .height(IMAGE_HEIGHT),
        item = DisplayItem.Movie(
            "1",
            "https://image.tmdb.org/t/p/w500/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg",
            "https://image.tmdb.org/t/p/w500/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg"
        ),
        onItemClick = {}
    )
}
