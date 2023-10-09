package pl.jsyty.architecturetemplate.feature.dashboard.impl

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deliveryhero.whetstone.fragment.ContributesFragment
import org.orbitmvi.orbit.compose.collectAsState
import pl.jsyty.architecturetemplate.feature.dashboard.DashboardDirection
import pl.jsyty.architecturetemplate.feature.message.MessageNavigationResult
import pl.jsyty.architecturetemplate.feature.weather.WeatherDirection
import pl.jsyty.architecturetemplate.features.longaction.LongActionDirection
import pl.jsyty.architecturetemplate.ui.*
import pl.jsyty.architecturetemplate.ui.helpers.RegisterForNavigationResult
import pl.jsyty.architecturetemplate.ui.theme.ArchitectureTemplateTheme
import javax.inject.Inject

@ContributesFragment
class DashboardFragment @Inject constructor() :
    BaseDirectableComposeFragment<DashboardDirection>() {
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val viewModel = composeViewModel<DashboardViewModel>()
            val state by viewModel.collectAsState()
            DashboardPanel(
                state = state,
                onMessageCreate = viewModel::createMessage,
                onNameUpdate = viewModel::setName
            )

            RegisterForNavigationResult(MessageNavigationResult, onResultReturn = { fullMessage ->
                viewModel.setFullMessage(fullMessage)
            })
        }
    }

    @Composable
    private fun DashboardPanel(
        state: DashboardViewModel.State,
        onMessageCreate: () -> Unit,
        onNameUpdate: (String) -> Unit,
    ) {
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
            TextField(
                value = state.name,
                onValueChange = onNameUpdate,
                label = { Text(text = "Name") }
            )
            Button(onClick = onMessageCreate, enabled = state.name.isNotBlank()) {
                Text(text = "Create message")
            }
            val navigationController = LocalNavigationController.current

            Button(onClick = { navigationController.push(LongActionDirection) }) {
                Text(text = "Navigate to long action screen")
            }
            Button(onClick = { navigationController.push(WeatherDirection) }) {
                Text(text = "Navigation to weather screen")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        ArchitectureTemplateTheme {
            DashboardPanel(
                state = DashboardViewModel.State(returnedMessage = "test"),
                onMessageCreate = {},
                onNameUpdate = {}
            )
        }
    }
}
