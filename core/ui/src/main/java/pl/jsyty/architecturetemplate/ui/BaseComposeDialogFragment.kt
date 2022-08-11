package pl.jsyty.architecturetemplate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pl.jsyty.architecturetemplate.ui.theme.ArchitectureTemplateTheme

/**
 * Base fragment for usage with composable screen.
 *
 * [Content] method will be called inside the [ComposeView] with theme provider applied
 */
abstract class BaseComposeDialogFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ThemeOverlay_App_BottomSheetDialog)
    }

    /**
     * Override this to provide composable screen for this fragment
     */
    @Composable
    abstract fun Content()

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            ArchitectureTemplateTheme {
                    this@BaseComposeDialogFragment.Content()
            }
        }
    }

    fun setDraggable(isDraggable: Boolean) {
        (dialog as? BottomSheetDialog)?.behavior?.isDraggable = isDraggable
    }
}