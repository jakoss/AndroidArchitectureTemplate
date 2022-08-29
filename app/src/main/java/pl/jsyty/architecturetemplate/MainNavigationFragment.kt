package pl.jsyty.architecturetemplate

import android.os.Build
import android.os.Bundle
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ncapdevi.fragnav.*
import kotlinx.coroutines.launch
import pl.jsyty.architecturetemplate.feature.dashboard.DashboardDirection
import pl.jsyty.architecturetemplate.infrastructure.animR
import pl.jsyty.architecturetemplate.infrastructure.di.ComponentHolder
import pl.jsyty.architecturetemplate.infrastructure.navigation.NavigationComponent
import pl.jsyty.architecturetemplate.infrastructure.navigation.NavigationEvent
import timber.log.Timber

/**
 * Navigation host fragment - all backstack management happens here.
 *
 * It's basically the implementation of navigation events that's translated to fragments backstack.
 */
class MainNavigationFragment : Fragment(R.layout.fragment_main_navigation) {
    private val navigationComponent by lazy { ComponentHolder.component<NavigationComponent>() }

    private val fragNavController by lazy {
        FragNavController(
            childFragmentManager,
            R.id.fragmentContainer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentResolver = navigationComponent.navigationFragmentResolver()

        fragNavController.apply {
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Timber.e(throwable, message)
                }
            }

            defaultTransactionOptions = FragNavTransactionOptions.newBuilder().customAnimations(
                animR.slide_left_from_right,
                animR.slide_left_from_middle,
                animR.slide_right_from_left,
                animR.slide_right_from_middle
            ).build()

            val startFragment = fragmentResolver.resolveFragment(DashboardDirection)
            rootFragments = listOf(startFragment)
            initialize(savedInstanceState = savedInstanceState)
        }

        handleBackPress()
    }

    private fun handleBackPress() {
        fun runBackAction() {
            if (fragNavController.isRootFragment) {
                requireActivity().finish()
            } else {
                if (fragNavController.popFragment().not()) {
                    requireActivity().finish()
                }
            }
        }

        if (Build.VERSION.SDK_INT >= 33) {
            requireActivity().onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                runBackAction()
            }
        } else {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                runBackAction()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            navigationComponent.navigationEventsProvider().navigationEvents
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    Timber.i("Handling navigation event: $it")
                    when (it) {
                        is NavigationEvent.Pop -> {
                            if (fragNavController.currentDialogFrag != null) {
                                fragNavController.clearDialogFragment()
                            } else {
                                fragNavController.popFragments(it.level)
                            }
                        }
                        NavigationEvent.PopToRoot -> fragNavController.clearStack()
                        is NavigationEvent.PopToRootAndReplace -> {
                            fragNavController.clearStack()
                            fragNavController.replaceFragment(it.fragment)
                        }
                        is NavigationEvent.PopToRootAndPush -> {
                            fragNavController.clearStack()
                            fragNavController.pushFragment(it.fragment)
                        }
                        is NavigationEvent.PushFragment -> fragNavController.pushFragment(it.fragment)
                        is NavigationEvent.ReplaceFragment -> fragNavController.replaceFragment(
                            it.fragment
                        )
                        is NavigationEvent.ShowDialog -> fragNavController.showDialogFragment(
                            it.fragment
                        )
                    }
                }
        }
    }
}
