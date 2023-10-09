package pl.jsyty.architecturetemplate.infrastructure.viewmodel

import kotlinx.coroutines.flow.*
import org.orbitmvi.orbit.syntax.simple.*
import pl.jsyty.architecturetemplate.infrastructure.async.*
import timber.log.Timber

internal class AsyncContextFlowImpl<STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any>(
    private val action: Flow<RESOURCE>,
    private val simpleSyntaxContext: SimpleSyntax<STATE, SIDE_EFFECT>,
) : AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
    private var customErrorHandler: (suspend (Throwable) -> Unit)? = null

    override suspend fun execute(
        cachedValue: Async<RESOURCE>?,
        reducer: SimpleContext<STATE>.(Async<RESOURCE>) -> STATE,
    ) {
        var flowValue: RESOURCE? = null
        action.onStart {
            simpleSyntaxContext.reduce { reducer(Loading(cachedValue?.invoke())) }
        }.onEach {
            flowValue = it
            simpleSyntaxContext.reduce { reducer(Success(it)) }
        }.catch { ex ->
            Timber.e(ex)
            customErrorHandler?.invoke(ex)
            simpleSyntaxContext.reduce { reducer(Fail(ex, flowValue ?: cachedValue?.invoke())) }
        }.collect()
    }

    override fun handleError(errorHandler: suspend (Throwable) -> Unit): AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
        customErrorHandler = errorHandler
        return this
    }
}

/**
 * Creates an [AsyncContext] instance that will handle all [Async] state for you
 *
 * @see [AsyncContext]
 */
fun <STATE : Any, SIDE_EFFECT : Any, RESOURCE : Any> SimpleSyntax<STATE, SIDE_EFFECT>.asyncFlow(flow: Flow<RESOURCE>): AsyncContext<STATE, SIDE_EFFECT, RESOURCE> {
    return AsyncContextFlowImpl(flow, this)
}
