// ktlint-disable filename
package pl.jsyty.architecturetemplate.ui.async

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import pl.jsyty.architecturetemplate.infrastructure.async.*
import pl.jsyty.architecturetemplate.ui.components.FullscreenError
import pl.jsyty.architecturetemplate.ui.components.FullscreenLoader

/**
 * Adds a fullscreen handler for whole [Async] states.
 * This handler is specialized for components that should take whole available space.
 * Fullscreen components are basically "fillMaxSize"
 *
 * @param state State to be handled
 * @param retryAction Handler for "try again" click
 * @param error Component for error state. Defaults to [FullscreenError]
 * @param loading Component for loading state. Defaults to [FullscreenLoader]
 * @param uninitialized Component for uninitialized state. Defaults to [FullscreenLoader]
 * @param success Component for success state
 */
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
