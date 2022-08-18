package pl.jsyty.architecturetemplate.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FullscreenError(
    modifier: Modifier = Modifier,
    retryAction: (() -> Unit)? = null,
){
    Box(modifier = Modifier.fillMaxSize().then(modifier), contentAlignment = Alignment.Center){
        Column {
            Text(text = "Error!")
            if (retryAction != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = retryAction) {
                    Text(text = "Retry")
                }
            }
        }
    }
}
