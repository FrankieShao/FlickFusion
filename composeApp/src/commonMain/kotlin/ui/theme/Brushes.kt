package ui.theme

import androidx.compose.ui.graphics.Brush

/**
 * @author Frank Shao
 * @created 04/06/2024
 * Description: some common brushes
 */

val PART_BACKGROUND_GRADIENT = Brush.verticalGradient(
    0.0f to BG_Purple_Start,
    0.3f to BG_Purple_INT,
    1.0f to BG_Purple_End
)

val ALL_BACKGROUND_GRADIENT = Brush.verticalGradient(
    0.0f to BG_Purple_Start,
    0.75f to BG_Purple_INT,
    1.0f to BG_Purple_End
)
