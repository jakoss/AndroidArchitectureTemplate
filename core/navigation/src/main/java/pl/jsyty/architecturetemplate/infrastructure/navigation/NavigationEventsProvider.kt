package pl.jsyty.architecturetemplate.infrastructure.navigation

import kotlinx.coroutines.flow.Flow

interface NavigationEventsProvider {
    val navigationEvents: Flow<NavigationEvent>
}
