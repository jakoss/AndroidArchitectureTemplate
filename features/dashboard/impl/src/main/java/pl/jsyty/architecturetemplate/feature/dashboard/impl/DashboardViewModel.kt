package pl.jsyty.architecturetemplate.feature.dashboard.impl

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.NavigationController
import tangle.viewmodel.VMInject

class DashboardViewModel @VMInject constructor(
    private val navigationController: NavigationController,
) : ContainerHost<DashboardViewModel.State, Unit>, ViewModel() {
    data class State(
        val returnedMessage: String? = null,
        val name: String = "",
    )

    override val container = container<State, Unit>(State())

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
