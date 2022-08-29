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

    /**
     * @see NavigationController.push
     */
    override fun push(direction: Direction) {
        emitNavigationEvent(
            NavigationEvent.PushFragment(
                resolveFragment(direction)
            )
        )
    }

    /**
     * @see NavigationController.showDialog
     */
    override fun showDialog(direction: Direction) {
        val fragment = resolveFragment(direction)
        require(fragment is DialogFragment) {
            "Direction does not point to dialog fragment"
        }
        emitNavigationEvent(NavigationEvent.ShowDialog(fragment))
    }

    /**
     * @see NavigationController.pop
     */
    override fun pop(level: Int) {
        emitNavigationEvent(NavigationEvent.Pop(level))
    }

    /**
     * @see NavigationController.popToRoot
     */
    override fun popToRoot() {
        emitNavigationEvent(NavigationEvent.PopToRoot)
    }

    /**
     * @see NavigationController.popToRootAndPush
     */
    override fun popToRootAndPush(direction: Direction) {
        emitNavigationEvent(
            NavigationEvent.PopToRootAndPush(
                resolveFragment(direction)
            )
        )
    }

    /**
     * @see NavigationController.replace
     */
    override fun replace(direction: Direction) {
        emitNavigationEvent(
            NavigationEvent.ReplaceFragment(
                resolveFragment(direction)
            )
        )
    }

    /**
     * @see NavigationController.popToRootAndReplace
     */
    override fun popToRootAndReplace(direction: Direction) {
        emitNavigationEvent(
            NavigationEvent.PopToRootAndReplace(
                resolveFragment(direction)
            )
        )
    }

    private fun emitNavigationEvent(event: NavigationEvent) {
        mutableNavigationEventsDispatcher.tryEmit(event)
    }

    /**
     * We have [handlers] map which represents which [Direction] has been bound to which [Fragment].
     * This funtion resolves proper [Fragment] for given [direction] and (if exists) attach arguments to this fragment.
     *
     * @param direction Direction that points to a [Fragment] we want in return
     * @return Resolved [Fragment] that was bound to [direction]
     *
     * @see Direction
     * @throws IllegalStateException if given [direction] was not bound to any [Fragment]
     */
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
