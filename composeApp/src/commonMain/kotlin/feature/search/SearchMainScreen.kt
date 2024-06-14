package feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import util.collectAsStateWithLifecycle
import core.domain.model.Genre
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import ui.theme.TRANSPARENT_WHITE_1
import ui.theme.TRANSPARENT_WHITE_2

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Search Main Screen
 */

@Composable
fun SearchMainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {}
        )
        GenresContainer()
    }
}

@Composable
fun GenresContainer(
    viewModel: GenresViewModel = koinInject(),
    onGenreClick: (String) -> Unit = {}
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    if (uiState is UiState.HasData) {
        Column (modifier = Modifier.padding(top = 20.dp)){
            Text(
                text = "Genres",
                fontSize = 15.sp,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 8.dp, bottom = 12.dp)
            )
            GenreList((uiState as UiState.HasData).data)
        }
    }
}

@Composable
fun GenreList(data: List<Genre>) {
    LazyHorizontalStaggeredGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        rows = StaggeredGridCells.Fixed(4),
        horizontalItemSpacing = 3.dp,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items(data.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 2.dp, start = 4.dp, end = 4.dp)
                        .background(
                            color = TRANSPARENT_WHITE_1,
                            shape = RoundedCornerShape(4.dp),
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = data[index].name,
                        fontSize = 13.sp,
                        color = TRANSPARENT_WHITE_2
                    )
                }
            }
        }
    )
}

@Preview()
@Composable
fun GenreListPreview() {
    GenreList(
        data = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Comedy"),
            Genre(4, "Drama"),
            Genre(5, "Horror"),
            Genre(6, "Romance"),
            Genre(7, "Sci-Fi"),
            Genre(8, "Thriller"),
            Genre(9, "Western"),
            Genre(10, "Animation"),
            Genre(11, "Documentary"),
            Genre(12, "Family"),
            Genre(13, "Fantasy"),
            Genre(14, "History"),
            Genre(15, "Music"),
            Genre(16, "Mystery"),
            Genre(17, "War"),
            Genre(18, "Crime"),
            Genre(19, "TV Movie"),
            Genre(20, "Reality"),
        )
    )
}


