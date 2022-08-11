package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass


/**
 * This objects holds all registered navigation directions
 *
 * TODO : create aggregations automatically via the Anvil plugin
 */
object DirectionAggregator {
    internal val directions = mutableMapOf<KClass<Direction>, FragmentFactory>()

    fun registerDirection(directionClass: KClass<Direction>, fragmentFactory: FragmentFactory) {
        if (directions.containsKey(directionClass)) error("This direction has already been registered")
        directions[directionClass] = fragmentFactory
    }

    inline fun <reified T: Direction> registerDirection(crossinline fragmentFactory: () -> Fragment) {
        registerDirection(T::class as KClass<Direction>, object : FragmentFactory{
            override fun create() = fragmentFactory()
        })
    }
}