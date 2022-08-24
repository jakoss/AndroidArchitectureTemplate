package pl.jsyty.architecturetemplate.infrastructure.viewmodel

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

/**
 * All viewModels in the application should be based on this class.
 *
 * It's just a convenience helper over standard orbit-mvi [ContainerHost] <-> [ViewModel] integration.
 *
 * @param STATE State of the ViewModel
 * @param SIDE_EFFECT Side effect type that ViewModel can emit (pass [Unit] if you won't emit side effect). If you have multiple side effect use sealed class to represent those
 * @constructor Setup Orbit-MVI [container]
 *
 * @param initialState Initial state of ViewModel. In most cases it will just call parameterless [STATE] constructor to stick with default properties values.
 */
abstract class BaseViewModel<STATE : Any, SIDE_EFFECT : Any>(initialState: STATE) :
    ContainerHost<STATE, SIDE_EFFECT>, ViewModel() {
    override val container = container<STATE, SIDE_EFFECT>(initialState)
}
