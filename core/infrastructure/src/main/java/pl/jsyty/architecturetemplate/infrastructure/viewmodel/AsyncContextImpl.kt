package pl.jsyty.architecturetemplate.infrastructure.viewmodel

import kotlinx.coroutines.ensureActive
import org.orbitmvi.orbit.syntax.simple.*
import pl.jsyty.architecturetemplate.infrastructure.async.*
import timber.log.Timber
import kotlin.coroutines.coroutineContext

/**
 * @see [AsyncContext]
 */
internal class AsyncContextImpl<STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any>(
    private val action: suspend (STATE) -> RESOURCE,
    private val simpleSyntaxContext: SimpleSyntax<STATE, SIDE_EFFECT>,
) : AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
    private var customErrorHandler: (suspend (Throwable) -> Unit)? = null

    override suspend fun execute(cachedValue: Async<RESOURCE>?, reducer: SimpleContext<STATE>.(Async<RESOURCE>) -> STATE) {
        try {
            simpleSyntaxContext.reduce { reducer(Loading(cachedValue?.invoke())) }
            val result = action(simpleSyntaxContext.state)
            simpleSyntaxContext.reduce { reducer(Success(result)) }
        } catch (ex: Throwable) {
            coroutineContext.ensureActive()
            Timber.e(ex)
            customErrorHandler?.invoke(ex)
            simpleSyntaxContext.reduce { reducer(Fail(ex, cachedValue?.invoke())) }
        }
    }

    override fun handleError(errorHandler: suspend (Throwable) -> Unit): AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
        customErrorHandler = errorHandler
        return this
    }
}

/**
 * Creates an [AsyncContext] instance that will handle all [Async] flow state for you
 *
 * @see [AsyncContext]
 */
fun <STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any> SimpleSyntax<STATE, SIDE_EFFECT>.async(
    action: suspend (STATE) -> RESOURCE,
): AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
    return AsyncContextImpl(action, this)
}
