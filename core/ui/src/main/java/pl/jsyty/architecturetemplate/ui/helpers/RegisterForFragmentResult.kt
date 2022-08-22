package pl.jsyty.architecturetemplate.ui.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.LifecycleOwner
import pl.jsyty.architecturetemplate.infrastructure.navigation.NavigationResult
import pl.jsyty.architecturetemplate.ui.LocalChildFragmentManager

@Composable
fun <T : Any> RegisterForFragmentResult(
    navigationResult: NavigationResult<T>,
    fragmentManager: FragmentManager = LocalChildFragmentManager.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    callback: (T) -> Unit,
) {
    LaunchedEffect(Unit) {
        fragmentManager.setFragmentResultListener(navigationResult.resultKey, lifecycleOwner) { _, bundle ->
            @Suppress("UNCHECKED_CAST", "DEPRECATION")
            val result = bundle.get(navigationResult.parameterKey) as T
            callback(result)
        }
    }
}

fun <T : Any> Fragment.setNavigationResult(navigationResult: NavigationResult<T>, value: T) {
    setFragmentResult(
        navigationResult.resultKey,
        bundleOf(
            navigationResult.parameterKey to value
        )
    )
}
