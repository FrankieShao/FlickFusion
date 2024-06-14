package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.Constant
import kotlin.math.roundToInt

/**
 * @author Frank Shao
 * @created 28/05/2024
 * Description: Default indicator for pull to refresh.
 *              It is using Lottie animation.
 */
@Composable
fun DefaultRefreshIndicator(
    progress: Float,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    threshold: Int = Constant.Default.INDICATOR_HEIGHT,
    animatorSpec: AnimatorSpec
) {
    val rowHeight = if (isRefreshing) {
        threshold.dp
    } else {
        (progress * threshold).roundToInt().dp
    }
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(rowHeight)
            .padding(top = 16.dp)
    ) {
        AnimationLoader(
            modifier = Modifier
                .height(animatorSpec.height.dp)
                .width(animatorSpec.width.dp),
            isPlaying = isRefreshing,
            lottieString = Lottie_Loading
        )
    }
}