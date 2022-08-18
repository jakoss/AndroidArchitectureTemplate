package pl.jsyty.architecturetemplate.infrastructure.navigation.impl

import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import pl.jsyty.architecturetemplate.infrastructure.di.AppScope
import pl.jsyty.architecturetemplate.infrastructure.navigation.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(AppScope::class, boundType = NavigationController::class)
@ContributesBinding(AppScope::class, boundType = NavigationEventsProvider::class)
@ContributesBinding(AppScope::class, boundType = NavigationFragmentResolver::class)
class NavigationControllerImpl @Inject constructor(
    private val handlers: Map<String, @JvmSuppressWildcards FragmentFactory>,
) :
    NavigationController,
    NavigationEventsProvider,
    NavigationFragmentResolver {

    private val mutableNavigationEventsDispatcher = MutableSharedFlow<NavigationEvent>(
        extraBufferCapacity = 5,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val navigationEvents = mutableNavigationEventsDispatcher.asSharedFlow()

    override fun push(direction: Direction) {
        navigate(
            NavigationEvent.PushFragment(
                resolveFragment(direction)
            )
        )
    }

    override fun showDialog(direction: Direction) {
        val fragment = resolveFragment(direction)
        if (fragment !is DialogFragment) error("Direction does not point to dialog fragment")
        navigate(NavigationEvent.ShowDialog(fragment))
    }

    override fun pop(level: Int) {
        navigate(NavigationEvent.Pop(level))
    }

    override fun popToRoot() {
        navigate(NavigationEvent.PopToRoot)
    }

    override fun popToRootAndPush(direction: Direction) {
        navigate(
            NavigationEvent.PopToRootAndPush(
                resolveFragment(direction)
            )
        )
    }

    override fun replace(direction: Direction) {
        navigate(
            NavigationEvent.ReplaceFragment(
                resolveFragment(direction)
            )
        )
    }

    override fun popToRootAndReplace(direction: Direction) {
        navigate(
            NavigationEvent.PopToRootAndReplace(
                resolveFragment(direction)
            )
        )
    }

    private fun navigate(event: NavigationEvent) {
        mutableNavigationEventsDispatcher.tryEmit(event)
    }

    override fun resolveFragment(direction: Direction): Fragment {
        val factory =
            handlers[direction.javaClass.canonicalName ?: error("No canonical name for direction")]
        check(factory != null) {
            "Direction not registered"
        }
        val fragment = factory.create()
        fragment.arguments = bundleOf(ARGUMENT_KEY to direction)
        return fragment
    }
}
