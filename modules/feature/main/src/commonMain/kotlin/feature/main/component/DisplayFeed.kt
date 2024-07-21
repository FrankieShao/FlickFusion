package feature.main.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.main.DisplayMeta
import feature.main.FeedType
import org.real.flickfusion.Res
import org.real.flickfusion.ic_arrow
import org.jetbrains.compose.resources.vectorResource
import org.real.flickfusion.ui.DisplayItem
import org.real.flickfusion.ui.components.MediaBanner
import org.real.flickfusion.ui.components.SimpleDisplayRow
import org.real.flickfusion.ui.theme.SELECTED_COLOR

/**
 * @author Frank Shao
 * @created 02/06/2024
 * Description: Display Feed for Main Page, including Banner and Normal Feed
 */
@Composable
fun DisplayFeed(
    modifier: Modifier = Modifier,
    map: Map<DisplayMeta, List<DisplayItem>>,
    onItemClick: (String) -> Unit,
    onEnterList: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        val bannerMap = map.filterKeys { it.type == FeedType.BANNER }
        val normalMap = map.filterKeys { it.type == FeedType.NORMAL }
        bannerMap.forEach { (meta, items) ->
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = meta.title,
                        color = SELECTED_COLOR,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        MediaBanner(items, onItemClick = { onItemClick(it) })
                    }
                }
            }
        }
        normalMap.forEach { (meta, items) ->
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = meta.title,
                            style = MaterialTheme.typography.titleSmall,
                            color = SELECTED_COLOR
                        )
                        if (meta.showMore) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.ic_arrow),
                                tint = SELECTED_COLOR,
                                contentDescription = "Enter List",
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(28.dp)
                                    .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                                    .clickable { onEnterList(meta.title) }
                            )
                        }
                    }
                    SimpleDisplayRow(
                        modifier = Modifier.wrapContentHeight(),
                        items = items,
                        onItemClick = { onItemClick(it) }
                    )
                }
            }
        }
    }
}




