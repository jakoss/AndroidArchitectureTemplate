package pl.jsyty.architecturetemplate.infrastructure.viewmodel

import org.orbitmvi.orbit.syntax.simple.*
import pl.jsyty.architecturetemplate.infrastructure.async.*
import timber.log.Timber

/**
 * This will handle the [Async] state during an asynchronous call.
 * It will emit proper loading/success/error stated depenending on the asynchronous call step.
 */
interface AsyncContext<STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any> {
    suspend fun execute(cachedValue: Async<RESOURCE>? = null, reducer: SimpleContext<STATE>.(Async<RESOURCE>) -> STATE)
}

/**
 * @see [AsyncContext]
 */
internal class AsyncContextImpl<STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any>(
    private val action: suspend (STATE) -> RESOURCE,
    private val simpleSyntaxContext: SimpleSyntax<STATE, SIDE_EFFECT>,
) : AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
    override suspend fun execute(cachedValue: Async<RESOURCE>?, reducer: SimpleContext<STATE>.(Async<RESOURCE>) -> STATE) {
        try {
            simpleSyntaxContext.reduce { reducer(Loading(cachedValue?.invoke())) }
            val result = action(simpleSyntaxContext.state)
            simpleSyntaxContext.reduce { reducer(Success(result)) }
        } catch (ex: Throwable) {
            Timber.e(ex)
            simpleSyntaxContext.reduce { reducer(Fail(ex, cachedValue?.invoke())) }
        }
    }
}

/**
 * Creates an [AsyncContext] instance that will handle all [Async] state for you
 *
 * @see [AsyncContext]
 */
fun <STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any> SimpleSyntax<STATE, SIDE_EFFECT>.async(action: suspend (STATE) -> RESOURCE): AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
    return AsyncContextImpl(action, this)
}
