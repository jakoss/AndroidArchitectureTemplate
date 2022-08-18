package pl.jsyty.architecturetemplate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        savedInstanceState: Bundle?,
    ) = baseComposeSetup {
        Content()
    }

    fun setDraggable(isDraggable: Boolean) {
        (dialog as? BottomSheetDialog)?.behavior?.isDraggable = isDraggable
    }
}
