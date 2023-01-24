package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.fragment.app.Fragment

interface NavigationFragmentResolver {
    fun resolveFragment(direction: Direction): Fragment

    interface Factory {
        fun create(parentFragment: Fragment): NavigationFragmentResolver
    }
}
