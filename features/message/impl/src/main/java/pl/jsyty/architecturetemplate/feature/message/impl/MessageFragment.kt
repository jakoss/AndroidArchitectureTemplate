package pl.jsyty.architecturetemplate.feature.message.impl

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.feature.message.MessageNavigationResult
import pl.jsyty.architecturetemplate.ui.BaseDirectableComposeDialogFragment
import pl.jsyty.architecturetemplate.ui.LocalNavigationController
import pl.jsyty.architecturetemplate.ui.helpers.setNavigationResult
import pl.jsyty.architecturetemplate.ui.theme.ArchitectureTemplateTheme
import tangle.viewmodel.compose.tangleViewModel
import timber.log.Timber

class MessageFragment : BaseDirectableComposeDialogFragment<MessageDirection>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("Name passed via argument: ${direction.name}")
    }

    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            color = MaterialTheme.colors.background
        ) {
            val viewModel = tangleViewModel<MessageViewModel>()
            val state by viewModel.collectAsState()
            MessagePanel(
                state = state,
                updateMessage = viewModel::updateMessage,
                returnMessage = viewModel::returnMessage
            )

            val navigationController = LocalNavigationController.current
            viewModel.collectSideEffect {
                when (it) {
                    is MessageViewModel.SideEffects.ReturnMessage -> {
                        setNavigationResult(MessageNavigationResult, it.fullMessage)
                        navigationController.pop()
                    }
                }
            }
        }
    }

    @Composable
    private fun MessagePanel(
        state: MessageViewModel.State,
        updateMessage: (String) -> Unit,
        returnMessage: () -> Unit,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Provided name: ${state.name}")
            TextField(
                value = state.message,
                onValueChange = updateMessage,
                label = { Text(text = "Message") },
                keyboardActions = KeyboardActions(
                    onDone = {
                        returnMessage()
                    }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Button(onClick = returnMessage) {
                Text(text = "Return message")
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        ArchitectureTemplateTheme {
            MessagePanel(
                state = MessageViewModel.State(name = "Test name"),
                updateMessage = {},
                returnMessage = {}
            )
        }
    }
}
