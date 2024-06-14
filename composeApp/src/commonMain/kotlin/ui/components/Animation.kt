package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.LottieConstants
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition

/**
 * @author Frank Shao
 * @created 28/05/2024
 * Description: Animation loader for Lottie animation.
 */
@Composable
fun AnimationLoader(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    lottieString: String
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.JsonString(lottieString)
    )
    LottieAnimation(
        composition = preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        modifier = modifier
    )
}

data class AnimatorSpec(
    val resId: Int,
    val width: Int,
    val height: Int
)
