package pl.jsyty.architecturetemplate.feature.longaction.impl

import pl.jsyty.architecturetemplate.features.longaction.LongActionDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.DirectionAggregator

object LongActionModule {
    fun register() {
        // TODO : just temporary navigation registration site
        DirectionAggregator.registerDirection<LongActionDirection> { LongActionFragment() }
    }
}
