package pl.jsyty.architecturetemplate.feature.dashboard.impl

import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.NavigationController
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.BaseViewModel
import tangle.viewmodel.VMInject

class DashboardViewModel @VMInject constructor(
    private val navigationController: NavigationController,
) : BaseViewModel<DashboardViewModel.State, Unit>(State()) {
    data class State(
        val returnedMessage: String? = null,
        val name: String = "",
    )

    fun createMessage() = intent {
        navigationController.showDialog(MessageDirection(name = state.name))
        reduce {
            state.copy(name = "")
        }
    }

    fun setName(value: String) = intent {
        reduce {
            state.copy(name = value)
        }
    }

    fun setFullMessage(value: String) = intent {
        reduce {
            state.copy(returnedMessage = value)
        }
    }
}
