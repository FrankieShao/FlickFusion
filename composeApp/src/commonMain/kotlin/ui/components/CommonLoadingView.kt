package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: Loading view for common use
 */
@Composable
fun CommonLoadingView(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().height(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimationLoader(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp),
            isPlaying = true,
            lottieString = Lottie_Loading
        )
    }
}