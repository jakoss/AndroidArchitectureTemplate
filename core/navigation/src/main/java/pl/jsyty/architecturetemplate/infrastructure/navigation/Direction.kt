package pl.jsyty.architecturetemplate.infrastructure.navigation

import android.os.Parcelable
import dagger.MapKey

// Interface that marks class as a direction for navigation
interface Direction : Parcelable

/**
 * A [MapKey] for populating a map of [Direction] and fragment factories.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@MapKey
annotation class DirectionKey(val value: String) // KClass<out Direction>)
