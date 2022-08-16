package pl.jsyty.architecturetemplate.ui

import pl.jsyty.architecturetemplate.infrastructure.navigation.ARGUMENT_KEY
import pl.jsyty.architecturetemplate.infrastructure.navigation.Direction

abstract class BaseDirectionComposeDialogFragment<T : Direction> : BaseComposeDialogFragment() {
    protected val direction: T
        get() = @Suppress("DEPRECATION") arguments?.getParcelable(ARGUMENT_KEY) as? T
            ?: error("No argument found")
}
