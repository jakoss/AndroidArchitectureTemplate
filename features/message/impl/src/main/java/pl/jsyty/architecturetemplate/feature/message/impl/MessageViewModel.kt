package pl.jsyty.architecturetemplate.feature.message.impl

import androidx.lifecycle.SavedStateHandle
import com.deliveryhero.whetstone.viewmodel.ContributesViewModel
import org.orbitmvi.orbit.syntax.simple.*
import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.getNavigationArgument
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.BaseViewModel
import javax.inject.Inject

@ContributesViewModel
class MessageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<MessageViewModel.State, MessageViewModel.SideEffects>(State(name = savedStateHandle.getNavigationArgument<MessageDirection>().name)) {
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
