package pl.jsyty.architecturetemplate.feature.message

import kotlinx.parcelize.Parcelize
import pl.jsyty.architecturetemplate.infrastructure.navigation.Direction

@Parcelize
data class MessageDirection(val name: String) :
    Direction
