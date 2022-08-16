package pl.jsyty.architecturetemplate.feature.message.impl

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.*
import org.orbitmvi.orbit.viewmodel.container
import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.ARGUMENT_KEY
import tangle.inject.TangleParam
import tangle.viewmodel.VMInject

class MessageViewModel @VMInject constructor(
    @TangleParam(ARGUMENT_KEY)
    messageDirection: MessageDirection,
) : ContainerHost<MessageViewModel.State, MessageViewModel.SideEffects>, ViewModel() {
    data class State(
        val name: String,
        val message: String = "",
    )

    sealed class SideEffects {
        data class ReturnMessage(val fullMessage: String) : SideEffects()
    }

    override val container = container<State, SideEffects>(State(name = messageDirection.name))

    fun updateMessage(value: String) = intent {
        reduce { state.copy(message = value) }
    }

    fun returnMessage() = intent {
        val fullMessage = "Hello ${state.name}\n${state.message}"
        postSideEffect(SideEffects.ReturnMessage(fullMessage))
    }
}
