package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.lifecycle.SavedStateHandle

inline fun <reified T : Direction> SavedStateHandle.getNavigationArgument(): T =
    requireNotNull(this[ARGUMENT_KEY]) {
        "Navigation argument not provided or passed"
    }
