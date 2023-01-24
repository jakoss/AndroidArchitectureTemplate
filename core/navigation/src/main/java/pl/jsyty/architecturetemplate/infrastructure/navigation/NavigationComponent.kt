package pl.jsyty.architecturetemplate.infrastructure.navigation

import com.deliveryhero.whetstone.app.ApplicationScope
import com.squareup.anvil.annotations.ContributesTo

@ContributesTo(ApplicationScope::class)
interface NavigationComponent {
    fun navigationControllerProvider(): NavigationController
}
