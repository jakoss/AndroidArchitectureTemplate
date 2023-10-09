package pl.jsyty.architecturetemplate.infrastructure.async

import androidx.compose.runtime.Immutable

/**
 * Class that represents single asynchronous action in multiple states
 *
 * Code taken from Mavericks library: https://github.com/airbnb/mavericks/blob/main/mvrx-common/src/main/java/com/airbnb/mvrx/Async.kt
 *
 * @param T Type of resource that we're loading asynchronously
 * @property complete Did the operation finished? It will be true for both failed and succeded states
 * @property shouldLoad Should the resource be loaded? It will be true for uninitialized and failed states
 * @property value Value of loaded resource (null for failed, unitialized or loaded states)
 */
@Immutable
sealed class Async<out T>(val complete: Boolean, val shouldLoad: Boolean, private val value: T?) {
    /**
     * Returns the value or null.
     *
     * Success always have a value. Loading and Fail can also return a value which is useful for
     * pagination or progressive data loading.
     *
     * Can be invoked as an operator like: `yourProp()`
     */
    open operator fun invoke(): T? = value
}

@Immutable
object Uninitialized : Async<Nothing>(complete = false, shouldLoad = true, value = null), Incomplete

@Immutable
data class Loading<out T>(private val value: T? = null) : Async<T>(complete = false, shouldLoad = false, value = value), Incomplete

@Immutable
data class Success<out T>(private val value: T) : Async<T>(complete = true, shouldLoad = false, value = value) {

    override operator fun invoke(): T = value
}

@Immutable
data class Fail<out T>(val error: Throwable, private val value: T? = null) : Async<T>(complete = true, shouldLoad = true, value = value) {
    override fun equals(other: Any?): Boolean {
        if (other !is Fail<*>) return false

        val otherError = other.error
        return error::class == otherError::class &&
            error.message == otherError.message &&
            error.stackTrace.firstOrNull() == otherError.stackTrace.firstOrNull()
    }

    override fun hashCode(): Int = arrayOf(error::class, error.message, error.stackTrace.firstOrNull()).contentHashCode()
}

/**
 * Helper interface for using Async in a when clause for handling both Uninitialized and Loading.
 *
 * With this, you can do:
 * when (data) {
 *     is Incomplete -> Unit
 *     is Success    -> Unit
 *     is Fail       -> Unit
 * }
 */
@Immutable
interface Incomplete
