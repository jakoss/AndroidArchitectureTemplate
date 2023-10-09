package pl.jsyty.architecturetemplate.infrastructure.navigation

sealed class NavigationEvent {
    data class Push(
        val direction: Direction,
    ) : NavigationEvent()

    data class ReplaceFragment(
        val direction: Direction,
    ) : NavigationEvent()

    data class PopToRootAndReplace(
        val direction: Direction,
    ) : NavigationEvent()

    data class PopToRootAndPush(
        val direction: Direction,
    ) : NavigationEvent()

    data class ShowDialog(
        val direction: Direction,
    ) : NavigationEvent()
    data class Pop(val level: Int) :
        NavigationEvent()

    object PopToRoot : NavigationEvent()
}
