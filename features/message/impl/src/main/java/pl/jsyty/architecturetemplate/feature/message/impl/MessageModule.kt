package pl.jsyty.architecturetemplate.feature.message.impl

import pl.jsyty.architecturetemplate.feature.message.MessageDirection
import pl.jsyty.architecturetemplate.infrastructure.navigation.DirectionAggregator

object MessageModule {
    fun register() {
        // TODO : just temporary navigation registration site
        DirectionAggregator.registerDirection<MessageDirection> { MessageFragment() }
    }
}
