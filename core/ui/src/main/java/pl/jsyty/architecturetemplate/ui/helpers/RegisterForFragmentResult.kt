package pl.jsyty.architecturetemplate.ui.helpers

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import pl.jsyty.architecturetemplate.ui.LocalChildFragmentManager

@Composable
fun RegisterForFragmentResult(
    resultKey: String,
    fragmentManager: FragmentManager = LocalChildFragmentManager.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    callback: (key: String, bundle: Bundle) -> Unit,
) {
    LaunchedEffect(Unit) {
        fragmentManager.setFragmentResultListener(resultKey, lifecycleOwner, callback)
    }
}
