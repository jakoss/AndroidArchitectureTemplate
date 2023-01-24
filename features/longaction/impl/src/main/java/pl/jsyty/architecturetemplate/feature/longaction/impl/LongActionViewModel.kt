package pl.jsyty.architecturetemplate.feature.longaction.impl

import com.deliveryhero.whetstone.viewmodel.ContributesViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import pl.jsyty.architecturetemplate.infrastructure.async.Async
import pl.jsyty.architecturetemplate.infrastructure.async.Uninitialized
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.BaseViewModel
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.async
import javax.inject.Inject

@ContributesViewModel
class LongActionViewModel @Inject constructor() :
    BaseViewModel<LongActionViewModel.State, Unit>(State()) {
    data class State(
        val action: Async<String> = Uninitialized,
    )

    fun initialize(fail: Boolean) = intent {
        async {
            // simulate long running operation
            @Suppress("MagicNumber")
            delay(5000)
            if (fail) {
                @Suppress("TooGenericExceptionThrown")
                throw RuntimeException("Operation failed!")
            } else {
                "Success!"
            }
        }.execute {
            state.copy(action = it)
        }
    }
}
