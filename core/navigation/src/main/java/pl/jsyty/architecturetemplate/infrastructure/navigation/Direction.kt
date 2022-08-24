package pl.jsyty.architecturetemplate.infrastructure.navigation

import android.os.Parcelable
import dagger.MapKey

/**
 * Interface that marks class as a direction for navigation.
 *
 * Create a data class with [kotlinx.parcelize.Parcelize] annotation for argument passing.
 */
interface Direction : Parcelable

/**
 * A [MapKey] for populating a map of [Direction] and fragment factories.
 * This should never be done manually. Instead Anvil processor plugin will take care of that for us automatically during compilation.
 */
@Suppress("unused")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@MapKey
annotation class DirectionKey(val value: String)
