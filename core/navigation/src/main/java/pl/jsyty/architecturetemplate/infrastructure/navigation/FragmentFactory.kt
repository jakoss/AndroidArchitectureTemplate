package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.fragment.app.Fragment

/**
 * This interface will be implemented by Anvil processor plugin during compilation.
 *
 * It delegates cration of [Fragment] to class bound to specific [Direction]
 */
interface FragmentFactory {
    fun create(): Fragment
}
