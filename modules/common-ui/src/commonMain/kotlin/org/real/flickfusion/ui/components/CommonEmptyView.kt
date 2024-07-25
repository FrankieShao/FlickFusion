package org.real.flickfusion.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import org.real.flickfusion.ui.IUiState
import org.real.flickfusion.ui.theme.SELECTED_COLOR


/**
 * @author Frank Shao
 * @created 06/06/2024
 * Description: empty view for common use
 */
@Composable
fun CommonEmptyView(uiState: IUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize().testTag("CommonEmptyView"),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when {
                uiState.isEmpty -> "No Data, Try Again"
                uiState.isError -> "Something went wrong, Try Again"
                else -> "Loading"
            },
            color = SELECTED_COLOR,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentSize(),
            style = MaterialTheme.typography.titleSmall
        )
    }
}