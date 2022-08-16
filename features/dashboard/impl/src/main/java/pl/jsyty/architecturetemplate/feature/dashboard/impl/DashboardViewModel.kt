package pl.jsyty.architecturetemplate.feature.dashboard.impl

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import pl.jsyty.architecturetemplate.feature.dashboard.DashboardDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.ARGUMENT_KEY
import pl.jsyty.architecturetemplate.infrastructure.navigation.NavigationController
import tangle.inject.TangleParam
import tangle.viewmodel.VMInject
import timber.log.Timber

class DashboardViewModel @VMInject constructor(
    // TODO : wait for my pull request to tangle to be accepted
    // @TangleParam(ARGUMENT_KEY)
    // private val direction: DashboardDirection,
    private val navigationController: NavigationController,
    ) : ContainerHost<DashboardViewModel.State, Unit>, ViewModel() {
    data class State(
        val returnedMessage: String? = null,
    )

    override val container = container<State, Unit>(State())

    fun createMessage() {
        // val dir = direction
        // Timber.d("Direction: ${dir::class.java}")
    }
}
