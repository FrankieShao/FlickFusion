package ui.components.refresh.lottie

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import ui.components.Lottie_Loading
import ui.components.refresh.UltraSwipeRefreshState
import ui.components.refresh.lottie.LottieRefreshIndicator

/**
 * Lottie动画指示器
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Composable
fun LottieRefreshHeader(
    state: UltraSwipeRefreshState,
    modifier: Modifier = Modifier,
    spec: LottieCompositionSpec = LottieCompositionSpec.JsonString(Lottie_Loading),
    height: Dp = 60.dp,
    alignment: Alignment = Alignment.Center,
    speed: Float = 1f,
    contentScale: ContentScale = ContentScale.Fit,
) {
    LottieRefreshIndicator(
        state = state,
        isFooter = false,
        spec = spec,
        modifier = modifier,
        height = height,
        alignment = alignment,
        speed = speed,
        contentScale = contentScale
    )
}