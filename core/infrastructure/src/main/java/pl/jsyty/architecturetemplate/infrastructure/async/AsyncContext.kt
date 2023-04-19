package pl.jsyty.architecturetemplate.infrastructure.async

import org.orbitmvi.orbit.syntax.simple.SimpleContext

/**
 * This will handle the [Async] state during an asynchronous call.
 * It will emit proper loading/success/error stated depenending on the asynchronous call step.
 */
interface AsyncContext<STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any> {
    suspend fun execute(
        cachedValue: Async<RESOURCE>? = null,
        reducer: SimpleContext<STATE>.(Async<RESOURCE>) -> STATE,
    )

    fun handleError(errorHandler: suspend (Throwable) -> Unit): AsyncContext<STATE, SIDE_EFFECT, RESOURCE>
}
