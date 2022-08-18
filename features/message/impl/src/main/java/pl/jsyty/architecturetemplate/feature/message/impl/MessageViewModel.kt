package pl.jsyty.architecturetemplate.feature.message.impl

import org.orbitmvi.orbit.syntax.simple.*
import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.ARGUMENT_KEY
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.BaseViewModel
import tangle.inject.TangleParam
import tangle.viewmodel.VMInject

class MessageViewModel @VMInject constructor(
    @TangleParam(ARGUMENT_KEY)
    messageDirection: MessageDirection,
) : BaseViewModel<MessageViewModel.State, MessageViewModel.SideEffects>(State(name = messageDirection.name)) {
    data class State(
        val name: String,
        val message: String = "",
    )

    sealed class SideEffects {
        data class ReturnMessage(val fullMessage: String) : SideEffects()
    }

    fun updateMessage(value: String) = intent {
        reduce { state.copy(message = value) }
    }

    fun returnMessage() = intent {
        val fullMessage = "Hello ${state.name}\n${state.message}"
        postSideEffect(SideEffects.ReturnMessage(fullMessage))
    }
}
