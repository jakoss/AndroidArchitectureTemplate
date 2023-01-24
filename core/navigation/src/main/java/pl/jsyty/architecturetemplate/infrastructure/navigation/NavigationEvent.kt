package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

sealed class NavigationEvent {
    data class Push(
        val direction: Direction,
    ) : NavigationEvent()

    data class ReplaceFragment(
        val direction: Direction,
    ) : NavigationEvent()

    data class PopToRootAndReplace(
        val direction: Direction,
    ) : NavigationEvent()

    data class PopToRootAndPush(
        val direction: Direction,
    ) : NavigationEvent()

    data class ShowDialog(
        val direction: Direction,
    ) : NavigationEvent()
    data class Pop(val level: Int) :
        NavigationEvent()

    object PopToRoot : NavigationEvent()
}
