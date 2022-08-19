package pl.jsyty.architecturetemplate.infrastructure.navigation

abstract class NavigationResult<TParameter : Any> {
    val resultKey: String = this.javaClass.canonicalName ?: error("No canonical name for this type")
    val parameterKey: String = resultKey + "__ParameterKey"
}
