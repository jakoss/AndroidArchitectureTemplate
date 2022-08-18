package pl.jsyty.architecturetemplate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

/**
 * Base fragment for usage with composable screen.
 *
 * [Content] method will be called inside the [ComposeView] with theme provider applied
 */
abstract class BaseComposeFragment : Fragment() {
    /**
     * Override this to provide composable screen for this fragment
     */
    @Composable
    abstract fun Content()

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = baseComposeSetup {
        Content()
    }
}
