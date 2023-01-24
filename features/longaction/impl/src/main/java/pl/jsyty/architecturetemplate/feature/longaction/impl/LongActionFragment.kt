package pl.jsyty.architecturetemplate.feature.longaction.impl

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deliveryhero.whetstone.fragment.ContributesFragment
import org.orbitmvi.orbit.compose.collectAsState
import pl.jsyty.architecturetemplate.features.longaction.LongActionDirection
import pl.jsyty.architecturetemplate.ui.BaseDirectableComposeFragment
import pl.jsyty.architecturetemplate.ui.async.FullscreenAsyncHandler
import pl.jsyty.architecturetemplate.ui.composeViewModel
import javax.inject.Inject

@ContributesFragment
class LongActionFragment @Inject constructor() : BaseDirectableComposeFragment<LongActionDirection>() {
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val viewModel = composeViewModel<LongActionViewModel>()
            val state by viewModel.collectAsState()

            FullscreenAsyncHandler(
                state = state.action,
                onRetryAction = { viewModel.initialize(fail = false) },
                uninitialized = {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(text = "Resource loading uninitialized")

                        Button(onClick = { viewModel.initialize(fail = false) }) {
                            Text(text = "Load resource")
                        }
                        Button(onClick = { viewModel.initialize(fail = true) }) {
                            Text(text = "Load resource with failure")
                        }
                    }
                }
            ) {
                Text(text = "Loaded resource: $it")
            }
        }
    }
}
