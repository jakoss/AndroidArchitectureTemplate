package pl.jsyty.architecturetemplate.ui

import pl.jsyty.architecturetemplate.infrastructure.navigation.ARGUMENT_KEY
import pl.jsyty.architecturetemplate.infrastructure.navigation.Direction

/**
 * Marks this [androidx.fragment.app.Fragment] as directable.
 * Anvil process plugin will automatically bind this [androidx.fragment.app.Fragment] to given [T] direction.
 *
 * @param T Direcation that will be bind to this [androidx.fragment.app.Fragment]
 */
abstract class BaseDirectableComposeDialogFragment<T : Direction> : BaseComposeDialogFragment() {
    protected val direction: T
        @Suppress("DEPRECATION")
        get() =
            arguments?.getParcelable(ARGUMENT_KEY) as? T
                ?: error("No argument found")
}
