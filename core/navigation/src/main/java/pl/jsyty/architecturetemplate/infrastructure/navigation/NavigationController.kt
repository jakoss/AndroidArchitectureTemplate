package pl.jsyty.architecturetemplate.infrastructure.navigation

/**
 * Navigation controller that can be injected and used anywhere in the app (mostly from views, fragments or ViewModels).
 */
interface NavigationController {
    /**
     * Push given [Direction] to top of the stack
     */
    fun push(direction: Direction)

    /**
     * Pops the stack up given levels
     */
    fun pop(level: Int = 1)

    /**
     * Pops the stack up to the root direction
     */
    fun popToRoot()

    /**
     * Replace current screen with given [Direction]
     */
    fun replace(direction: Direction)

    /**
     * Pop the stack up to the root direction and replace root screen with given [Direction]
     */
    fun popToRootAndReplace(direction: Direction)

    /**
     * Pop the stack up to the root direction and push new screen with given [Direction]
     */
    fun popToRootAndPush(direction: Direction)

    /**
     * Display dialog
     */
    fun showDialog(direction: Direction)
}
