package pl.jsyty.architecturetemplate.feature.dashboard.impl

import pl.jsyty.architecturetemplate.feature.dashboard.DashboardDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.DirectionAggregator

object DashboardModule {
    fun register() {
        // TODO : just temporary navigation registration site
        DirectionAggregator.registerDirection<DashboardDirection> { DashboardFragment() }
    }
}
