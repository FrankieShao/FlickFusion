package org.real.flickfusion.ui.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.real.flickfusion.ui.theme.SELECTED_COLOR
import org.real.flickfusion.ui.theme.UNSELECTED_COLOR


/**
 * @author Frank Shao
 * @created 02/06/2024
 * Description: Page indicator with animation
 */
@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onSelectionChange: (Int) -> Unit,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 250, easing = FastOutSlowInEasing),
) {
    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(40.dp)
            .padding(8.dp)
    ) {
        val maxWidth = this.maxWidth
        val maxHeight = this.maxHeight
        val tabWidth = maxWidth / items.size
        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * selectedIndex,
            animationSpec = animationSpec,
            label = "indicator offset"
        )
        val offset = (tabWidth - 34.dp)/2
        Row(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawLine(
                        color = SELECTED_COLOR,
                        strokeWidth = 1.5.dp.toPx(),
                        start = Offset((indicatorOffset + offset).toPx(), (maxHeight - 1.dp).toPx()),
                        end = Offset((indicatorOffset + offset + 25.dp).toPx(), (maxHeight - 1.dp).toPx())
                    )
                }
        ) {
            items.forEachIndexed { index, text ->
                val color = if (selectedIndex == index) SELECTED_COLOR else UNSELECTED_COLOR
                Box(
                    modifier = Modifier
                        .width(tabWidth)
                        .fillMaxHeight()
                        .clickable(
                            onClick = {
                                onSelectionChange(index)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[index],
                        color = color,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier
                            .wrapContentSize()
                            .clickable { onSelectionChange(index) }
                    )
                }
            }
        }
    }
}