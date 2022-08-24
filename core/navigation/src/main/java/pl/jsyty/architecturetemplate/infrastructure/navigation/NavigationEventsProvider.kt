package pl.jsyty.architecturetemplate.infrastructure.navigation

import kotlinx.coroutines.flow.Flow

/**
 * Navigation events should be used by a backstack implementation to represent those events into a proper backstack.
 *
 * So when implementing your own backstack you can take [NavigationEventsProvider] from [NavigationComponent] and listen to this flow.
 */
interface NavigationEventsProvider {
    val navigationEvents: Flow<NavigationEvent>
}
