package favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.real.flickfusion.model.MediaModel
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.ui.theme.BG_Purple_INT_1
import org.real.flickfusion.ui.theme.TITLE_WHITE
import org.real.flickfusion.ui.theme.TRANSPARENT_WHITE_2
import org.real.flickfusion.ui.theme.UNSELECTED_COLOR

/**
 * @author Frank Shao
 * @created 11/06/2024
 * Description: Favorite Item

 */
@Deprecated("use poster")
@Composable
fun FavoriteItem(favoriteUiModel: MediaModel, onItemClicked: (MediaModel) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp).clickable { onItemClicked(favoriteUiModel) },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = BG_Purple_INT_1,
                contentColor = Color.Transparent,
            )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                val (poster, title, vote, genre, cast, overview) = createRefs()
                // poster
                AsyncImage(
                    model = favoriteUiModel.posterUrl,
                    placeholder = ColorPainter(color = Color.Gray),
                    contentDescription = "Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .constrainAs(poster) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            height = Dimension.value(90.dp)
                            width = Dimension.value(60.dp)
                        }
                )
                // title
                Text(
                    text = favoriteUiModel.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 14.sp,
                    color = TITLE_WHITE,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(poster.top)
                        start.linkTo(poster.end, margin = 10.dp)
                    })
                //vote
                Text(
                    text = "${favoriteUiModel.voteAverage}",
                    style = MaterialTheme.typography.labelMedium,
                    color = UNSELECTED_COLOR,
                    modifier = Modifier.constrainAs(vote) {
                        top.linkTo(title.bottom, margin = 10.dp)
                        start.linkTo(poster.end, margin = 10.dp)
                    })
                // genre
                Text(
                    text = favoriteUiModel.getGenres(),
                    style = MaterialTheme.typography.labelMedium,
                    color = UNSELECTED_COLOR,
                    modifier = Modifier.constrainAs(genre) {
                        top.linkTo(vote.bottom, margin = 5.dp)
                        start.linkTo(poster.end, margin = 10.dp)
                    })
                // cast
                Text(
                    text = favoriteUiModel.getCast(),
                    style = MaterialTheme.typography.labelMedium,
                    color = UNSELECTED_COLOR,
                    modifier = Modifier.constrainAs(cast) {
                        top.linkTo(genre.bottom, margin = 5.dp)
                        start.linkTo(poster.end, margin = 10.dp)
                    })
                // overview
                Text(
                    text = favoriteUiModel.overview,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = TRANSPARENT_WHITE_2,
                    modifier = Modifier.constrainAs(overview) {
                        top.linkTo(poster.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                        end.linkTo(parent.end, margin = 12.dp)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }

}

@Composable
@Preview()
fun FavoriteItemPreview() {
    FavoriteItem(
        favoriteUiModel = MediaModel(
            id = 1,
            title = "title",
            posterUrl = "https://image.tmdb.org/t/p/w500/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg",
            voteAverage = 8.0,
            releaseDate = "2024-06-06",
            type = MediaType.MOVIE,
            overview = "overview overview overview overview overview overview overview overview overview",
            country = "US",
            genres = listOf("Action", "Adventure"),
            cast = listOf(),
            backdropPath = "https://image.tmdb.org/t/p/w500/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg"
        )
    )
}