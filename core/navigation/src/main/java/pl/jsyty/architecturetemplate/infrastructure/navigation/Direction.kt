package pl.jsyty.architecturetemplate.infrastructure.navigation

import android.os.Parcelable
import dagger.MapKey
import kotlin.reflect.KClass

// Interface that marks class as a direction for navigation
interface Direction : Parcelable

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindDirection(
    val to: KClass<out Direction>,
)

/**
 * A [MapKey] for populating a map of [Direction] and fragment factories.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@MapKey
annotation class DirectionKey(val value: String) // KClass<out Direction>)
