package pl.jsyty.architecturetemplate.ui.async

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import pl.jsyty.architecturetemplate.infrastructure.async.*
import pl.jsyty.architecturetemplate.ui.components.FullscreenError
import pl.jsyty.architecturetemplate.ui.components.FullscreenLoader

@Composable
fun <T> FullscreenAsyncHandler(
    state: Async<T>,
    retryAction: () -> Unit,
    error: @Composable (Throwable) -> Unit = { FullscreenError(retryAction = retryAction) },
    loading: @Composable () -> Unit = { FullscreenLoader() },
    uninitialized: @Composable () -> Unit = { FullscreenLoader() },
    success: @Composable (T) -> Unit,
) {
    Crossfade(targetState = state) {
        when (it) {
            Uninitialized -> uninitialized()
            is Loading -> loading()
            is Fail -> error(it.error)
            is Success -> success(it())
        }
    }
}
