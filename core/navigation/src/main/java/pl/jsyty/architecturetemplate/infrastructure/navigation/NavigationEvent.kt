package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

sealed class NavigationEvent {
    data class PushFragment(
        val fragment: Fragment,
    ) : NavigationEvent()

    data class ReplaceFragment(
        val fragment: Fragment,
    ) : NavigationEvent()

    data class PopToRootAndReplace(
        val fragment: Fragment,
    ) : NavigationEvent()

    data class PopToRootAndPush(
        val fragment: Fragment,
    ) : NavigationEvent()

    data class ShowDialog(val fragment: DialogFragment) : NavigationEvent()
    data class Pop(val level: Int) :
        NavigationEvent()

    object PopToRoot : NavigationEvent()
}
