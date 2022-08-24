package pl.jsyty.architecturetemplate.ui

import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pl.jsyty.architecturetemplate.infrastructure.di.ComponentHolder
import pl.jsyty.architecturetemplate.infrastructure.navigation.*
import pl.jsyty.architecturetemplate.ui.theme.ArchitectureTemplateTheme

private val navigationComponent by lazy { ComponentHolder.component<NavigationComponent>() }

/**
 * Standard setup for all compose-based fragments
 *
 * @param content Content of screen that will be displayed
 */
fun Fragment.baseComposeSetup(content: @Composable () -> Unit) =
    ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            CompositionLocalProvider(
                LocalNavigationController provides navigationComponent.navigationControllerProvider(),
                LocalChildFragmentManager provides this@baseComposeSetup.childFragmentManager,
                LocalParentFragmentManager provides this@baseComposeSetup.parentFragmentManager
            ) {
                ArchitectureTemplateTheme {
                    content()
                }
            }
        }
    }

private val navigationControllerFake = object :
    NavigationController {
    override fun push(direction: Direction) {
        error("Used fake navigation controller")
    }

    override fun pop(level: Int) {
        error("Used fake navigation controller")
    }

    override fun popToRoot() {
        error("Used fake navigation controller")
    }

    override fun replace(direction: Direction) {
        error("Used fake navigation controller")
    }

    override fun popToRootAndReplace(direction: Direction) {
        error("Used fake navigation controller")
    }

    override fun popToRootAndPush(direction: Direction) {
        error("Used fake navigation controller")
    }

    override fun showDialog(direction: Direction) {
        error("Used fake navigation controller")
    }
}

val LocalNavigationController = compositionLocalOf<NavigationController> { navigationControllerFake }
val LocalChildFragmentManager = compositionLocalOf<FragmentManager> { object : FragmentManager() {} }
val LocalParentFragmentManager = compositionLocalOf<FragmentManager> { object : FragmentManager() {} }
