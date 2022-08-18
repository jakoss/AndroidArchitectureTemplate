package pl.jsyty.architecturetemplate.infrastructure.navigation

import androidx.fragment.app.Fragment

interface FragmentFactory {
    fun create(): Fragment
}
