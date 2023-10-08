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
import com.deliveryhero.whetstone.fragment.ContributesFragment
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.feature.message.MessageNavigationResult
import pl.jsyty.architecturetemplate.ui.*
import pl.jsyty.architecturetemplate.ui.helpers.setNavigationResult
import pl.jsyty.architecturetemplate.ui.theme.ArchitectureTemplateTheme
import timber.log.Timber
import javax.inject.Inject

@ContributesFragment
class MessageFragment
    @Inject
    constructor() :
    BaseDirectableComposeDialogFragment<MessageDirection>() {
        override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?,
        ) {
            super.onViewCreated(view, savedInstanceState)

            Timber.d("Name passed via argument: ${direction.name}")
        }

        @Composable
        override fun Content() {
            Surface(
                modifier =
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                color = MaterialTheme.colors.background
            ) {
                val viewModel = composeViewModel<MessageViewModel>()
                val state by viewModel.collectAsState()
                MessagePanel(
                    state = state,
                    onMessageUpdate = viewModel::updateMessage,
                    onMessageReturn = viewModel::returnMessage
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
            onMessageUpdate: (String) -> Unit,
            onMessageReturn: () -> Unit,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Provided name: ${state.name}")
                TextField(
                    value = state.message,
                    onValueChange = onMessageUpdate,
                    label = { Text(text = "Message") },
                    keyboardActions =
                        KeyboardActions(
                            onDone = {
                                onMessageReturn()
                            }
                        ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
                Button(onClick = onMessageReturn) {
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
                    onMessageUpdate = {},
                    onMessageReturn = {}
                )
            }
        }
    }
