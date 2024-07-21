package profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil3.compose.AsyncImage
import org.real.flickfusion.model.AccountInfo
import org.real.flickfusion.ui.components.CommonEmptyView
import org.real.flickfusion.ui.components.CommonLoadingView
import org.real.flickfusion.ui.theme.BG_Purple_INT_1
import org.real.flickfusion.ui.theme.TRANSPARENT_WHITE_2

/**
 * @author Frank Shao
 * @created 30/05/2024
 * Description: mine page
 */
@Composable
fun MineScreen(uiState: UiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 42.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            uiState.isRefreshing -> {
                CommonLoadingView(
                    modifier = Modifier.padding(top = 100.dp),
                )
            }

            uiState is UiState.HasData -> {
                MineContent(uiState.data, uiState.urlPrefix)
            }

            else -> {
                CommonEmptyView(uiState)
            }
        }
    }
}

@Composable
fun MineContent(accountInfo: AccountInfo, urlPrefix: String) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = BG_Purple_INT_1,
            contentColor = Color.Transparent,
        )
    )  {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            val (avatar, name, user_name) = createRefs()
            AsyncImage(
                model = "$urlPrefix${accountInfo.avatar.tmdb.avatarPath}",
                placeholder = ColorPainter(color = Color.Gray),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .constrainAs(avatar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.value(90.dp)
                        width = Dimension.value(90.dp)
                    }
            )
            Text(
                text = accountInfo.name,
                color = Color.White,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(avatar.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Text(
                text = accountInfo.username,
                color = TRANSPARENT_WHITE_2,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.constrainAs(user_name) {
                    top.linkTo(name.bottom, margin = 3.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}