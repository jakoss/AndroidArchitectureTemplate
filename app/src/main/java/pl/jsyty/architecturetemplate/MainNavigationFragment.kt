package pl.jsyty.architecturetemplate

import android.os.Build
import android.os.Bundle
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.deliveryhero.whetstone.fragment.ContributesFragment
import com.ncapdevi.fragnav.*
import kotlinx.coroutines.launch
import pl.jsyty.architecturetemplate.feature.dashboard.DashboardDirection
import pl.jsyty.architecturetemplate.infrastructure.animR
import pl.jsyty.architecturetemplate.infrastructure.navigation.*
import pl.jsyty.architecturetemplate.infrastructure.navigation.impl.NavigationFragmentResolverImpl
import timber.log.Timber
import javax.inject.Inject

/**
 * Navigation host fragment - all backstack management happens here.
 *
 * It's basically the implementation of navigation events that's translated to fragments backstack.
 */
@ContributesFragment
class MainNavigationFragment @Inject constructor(
    private val fragmentResolverFactory: NavigationFragmentResolver.Factory,
    private val navigationEventsProvider: NavigationEventsProvider,
) : Fragment(R.layout.fragment_main_navigation) {
    private val fragNavController by lazy {
        FragNavController(
            childFragmentManager,
            R.id.fragmentContainer
        )
    }

    private val fragmentResolver by lazy {
        fragmentResolverFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        @Suppress("MagicNumber")
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
            navigationEventsProvider.navigationEvents
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
                            val fragment = fragmentResolver.resolveFragment(it.direction)
                            fragNavController.clearStack()
                            fragNavController.replaceFragment(fragment)
                        }
                        is NavigationEvent.PopToRootAndPush -> {
                            val fragment = fragmentResolver.resolveFragment(it.direction)
                            fragNavController.clearStack()
                            fragNavController.pushFragment(fragment)
                        }
                        is NavigationEvent.Push -> {
                            val fragment = fragmentResolver.resolveFragment(it.direction)
                            fragNavController.pushFragment(fragment)
                        }
                        is NavigationEvent.ReplaceFragment -> {
                            val fragment = fragmentResolver.resolveFragment(it.direction)
                            fragNavController.replaceFragment(
                                fragment
                            )
                        }
                        is NavigationEvent.ShowDialog -> {
                            val fragment = fragmentResolver.resolveFragment(it.direction)
                            require(fragment is DialogFragment) {
                                "Direction does not point to dialog fragment"
                            }
                            fragNavController.showDialogFragment(
                                fragment
                            )
                        }
                    }
                }
        }
    }
}
