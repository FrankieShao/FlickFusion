package detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.vectorResource
import org.real.flickfusion.Res
import org.real.flickfusion.ic_arrow
import org.real.flickfusion.ic_favorite
import org.real.flickfusion.model.Credits
import org.real.flickfusion.model.MediaModel
import org.real.flickfusion.ui.components.CommonEmptyView
import org.real.flickfusion.ui.components.CommonLoadingView
import org.real.flickfusion.ui.theme.BG_Purple_Start
import org.real.flickfusion.ui.theme.BUTTON_YELLOW
import org.real.flickfusion.ui.theme.TRANSPARENT_WHITE_2
import org.real.flickfusion.util.collectAsStateWithLifecycle

/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: Media Detail Screen
 */
@Composable
fun MediaDetailScreen(
    viewModel: MediaDetailViewModel,
    naviBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    when {
        state.isRefreshing -> {
            CommonLoadingView(
                modifier = Modifier.padding(top = 180.dp),
            )
        }

        state is UiState.HasData -> {
            MediaDetailContent((state as UiState.HasData).data) {
                naviBack()
            }
        }

        else -> {
            CommonEmptyView(state)
        }
    }
}

@Composable
fun MediaDetailContent(model: MediaModel, naviBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (title, returnButton) = createRefs()
            Icon(
                imageVector = vectorResource(Res.drawable.ic_arrow),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clickable(onClick = naviBack)
                    .graphicsLayer(scaleX = -1f)
                    .constrainAs(returnButton) {
                        top.linkTo(parent.top, margin = 7.dp)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Center,
                text = model.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
        AsyncImage(
            model = model.backdropPath,
            placeholder = ColorPainter(color = Color.Gray),
            contentDescription = "Backdrop",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(165.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(14.dp))
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (type, date, genre, like) = createRefs()
            Text(
                text = model.type.name,
                style = MaterialTheme.typography.labelMedium,
                color = TRANSPARENT_WHITE_2,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(type) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
            Text(
                text = model.releaseDate,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 8.sp,
                color = TRANSPARENT_WHITE_2,
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(type.top)
                    bottom.linkTo(type.bottom)
                    start.linkTo(type.end, margin = 10.dp)
                }
            )
            Text(
                text = model.getGenres(),
                style = MaterialTheme.typography.labelMedium,
                color = TRANSPARENT_WHITE_2,
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(genre) {
                    top.linkTo(type.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                }
            )
            Button(
                modifier = Modifier
                    .constrainAs(like) {
                        top.linkTo(parent.top, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BG_Purple_Start,
                    contentColor = Color.Transparent
                ),
                contentPadding = PaddingValues(start = 7.dp, end = 7.dp, top = 1.dp, bottom = 1.dp),
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_favorite),
                    tint = BUTTON_YELLOW,
                    contentDescription = "Like",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
                Spacer(modifier = Modifier.width(1.5.dp))
                Text(
                    text = "like",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 7.sp,
                    color = BUTTON_YELLOW
                )

            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Overview",
            style = MaterialTheme.typography.labelMedium,
            color = TRANSPARENT_WHITE_2,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = model.overview,
            style = MaterialTheme.typography.labelMedium,
            lineHeight = 17.sp,
            fontSize = 11.sp,
            color = TRANSPARENT_WHITE_2
        )
        Spacer(modifier = Modifier.height(14.dp))
        CastList(model.cast)
    }
}

@Composable
fun CastList(datas: List<Credits.Cast>) {
    Column {
        Text(
            text = "Cast",
            style = MaterialTheme.typography.labelMedium,
            color = TRANSPARENT_WHITE_2,
            fontSize = 14.5.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(end = 16.dp, bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = datas.size,
                key = { index -> datas[index].id }
            ) { index ->
                AsyncImage(
                    model = datas[index].profilePath,
                    placeholder = ColorPainter(color = Color.Gray),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(120.dp)
                        .width(75.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .shadow(3.dp, RoundedCornerShape(6.dp))
                )
            }
        }
    }
}
