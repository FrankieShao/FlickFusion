package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author Frank Shao
 * @created 12/06/2024
 * Description: to avoid crash: https://issuetracker.google.com/issues/336842920
 */
@Composable
@Suppress("StateFlowValueCalledInComposition") // Initial value for an ongoing collect.
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> = this.collectAsStateWithLifecycle(
    lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current,
    minActiveState = minActiveState,
    context = context
)


