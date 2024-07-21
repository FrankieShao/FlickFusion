package search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.real.flickfusion.Res
import org.real.flickfusion.ic_search
import org.real.flickfusion.ui.theme.TRANSPARENT_WHITE_1
import org.real.flickfusion.ui.theme.TRANSPARENT_WHITE_2

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: Search Bar
 */

@Composable
@Preview()
fun SearchBarPreview() {
    SearchBar {}
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    TextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
            onValueChange(it)
        },
        leadingIcon = {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_search),
                contentDescription = "Search Icon",
                modifier = Modifier
                    .width(28.dp)
                    .height(28.dp),
                tint = TRANSPARENT_WHITE_2
            )
        },
        placeholder = {
            Text(
                text = "Movies, Shows & more...",
                fontSize = 16.sp,
                color = TRANSPARENT_WHITE_2
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        shape = RoundedCornerShape(6.dp),
        colors = TextFieldDefaults.colors().copy(
            focusedTextColor = TRANSPARENT_WHITE_2,
            unfocusedTextColor = TRANSPARENT_WHITE_2,
            cursorColor = TRANSPARENT_WHITE_2,
            focusedContainerColor = TRANSPARENT_WHITE_1,
            unfocusedContainerColor = TRANSPARENT_WHITE_1,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
