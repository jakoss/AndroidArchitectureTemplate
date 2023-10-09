package pl.jsyty.architecturetemplate.infrastructure.navigation.impl

import com.deliveryhero.whetstone.app.ApplicationScope
import com.squareup.anvil.annotations.ContributesBinding
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import pl.jsyty.architecturetemplate.infrastructure.navigation.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(ApplicationScope::class, boundType = NavigationController::class)
@ContributesBinding(ApplicationScope::class, boundType = NavigationEventsProvider::class)
class NavigationControllerImpl @Inject constructor() :
    NavigationController,
    NavigationEventsProvider {
        private val mutableNavigationEventsDispatcher =
            MutableSharedFlow<NavigationEvent>(
                extraBufferCapacity = 5,
                onBufferOverflow = BufferOverflow.DROP_OLDEST
            )
        override val navigationEvents = mutableNavigationEventsDispatcher.asSharedFlow()

        /**
         * @see NavigationController.push
         */
        override fun push(direction: Direction) {
            emitNavigationEvent(
                NavigationEvent.Push(direction)
            )
        }

        /**
         * @see NavigationController.showDialog
         */
        override fun showDialog(direction: Direction) {
            emitNavigationEvent(NavigationEvent.ShowDialog(direction))
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
                    direction
                )
            )
        }

        /**
         * @see NavigationController.replace
         */
        override fun replace(direction: Direction) {
            emitNavigationEvent(
                NavigationEvent.ReplaceFragment(
                    direction
                )
            )
        }

        /**
         * @see NavigationController.popToRootAndReplace
         */
        override fun popToRootAndReplace(direction: Direction) {
            emitNavigationEvent(
                NavigationEvent.PopToRootAndReplace(
                    direction
                )
            )
        }

        private fun emitNavigationEvent(event: NavigationEvent) {
            mutableNavigationEventsDispatcher.tryEmit(event)
        }
    }
