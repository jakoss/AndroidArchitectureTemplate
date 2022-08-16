package pl.jsyty.architecturetemplate.feature.dashboard.impl

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import pl.jsyty.architecturetemplate.feature.dashboard.DashboardDirection
import pl.jsyty.architecturetemplate.ui.BaseDirectionComposeFragment
import pl.jsyty.architecturetemplate.ui.myViewModel
import pl.jsyty.architecturetemplate.ui.theme.ArchitectureTemplateTheme

class DashboardFragment : BaseDirectionComposeFragment<DashboardDirection>() {
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val viewModel = myViewModel<DashboardViewModel>()
            val state by viewModel.collectAsState()
            DashboardPanel(state = state, createMessage = viewModel::createMessage)
        }
    }

    @Composable
    private fun DashboardPanel(state: DashboardViewModel.State, createMessage: () -> Unit) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Welcome on the dashboard")
            if (state.returnedMessage == null) {
                Text(text = "No message returned yet")
            } else {
                Text(text = "Returned message: ${state.returnedMessage}")
            }
            Button(onClick = createMessage) {
                Text(text = "Create message")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        ArchitectureTemplateTheme {
            DashboardPanel(
                state = DashboardViewModel.State(returnedMessage = "test"),
                createMessage = {},
            )
        }
    }
}
