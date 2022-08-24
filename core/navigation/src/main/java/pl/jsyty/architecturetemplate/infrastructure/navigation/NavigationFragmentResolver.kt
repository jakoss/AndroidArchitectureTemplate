package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.fragment.app.Fragment

/**
 * This resolver can be used to resolve given [Direction] to a proper [Fragment].
 */
interface NavigationFragmentResolver {
    fun resolveFragment(direction: Direction): Fragment
}
