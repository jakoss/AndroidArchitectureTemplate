package pl.jsyty.architecturetemplate.feature.longaction.impl

import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import pl.jsyty.architecturetemplate.infrastructure.async.Async
import pl.jsyty.architecturetemplate.infrastructure.async.Uninitialized
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.BaseViewModel
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.async

class LongActionViewModel : BaseViewModel<LongActionViewModel.State, Unit>(State()) {
    data class State(
        val action: Async<String> = Uninitialized,
    )

    fun initialize(fail: Boolean) = intent {
        async {
            // simulate long running operation
            delay(5000)
            if (fail) {
                throw RuntimeException("Operation failed!")
            } else {
                "Success!"
            }
        }.execute {
            state.copy(action = it)
        }
    }
}
