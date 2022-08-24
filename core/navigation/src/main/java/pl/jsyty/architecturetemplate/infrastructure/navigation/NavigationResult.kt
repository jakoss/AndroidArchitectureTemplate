package pl.jsyty.architecturetemplate.infrastructure.navigation

/**
 * Represents a result that can be passed across navigation directions
 *
 * @param TParameter Type of result. Can be [android.os.Parcelable] or any other type that can be passed via [android.os.Bundle].
 */
abstract class NavigationResult<TParameter : Any> {
    val resultKey: String = this.javaClass.canonicalName ?: error("No canonical name for this type")
    val parameterKey: String = resultKey + "__ParameterKey"
}
