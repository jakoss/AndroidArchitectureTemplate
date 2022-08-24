// ktlint-disable filename
package pl.jsyty.architecturetemplate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Loading screen that will fill all available space
 *
 * @param modifier Modifier for the parent
 * @param color Color of the loading indicator
 * @param background Color of the background
 * @param alpha Alpha of the background (by default the background is semi-transparent)
 */
@Composable
fun FullscreenLoader(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    background: Color = MaterialTheme.colors.surface,
    alpha: Float = 0.6f,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = background.copy(alpha = alpha))
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = color)
    }
}
