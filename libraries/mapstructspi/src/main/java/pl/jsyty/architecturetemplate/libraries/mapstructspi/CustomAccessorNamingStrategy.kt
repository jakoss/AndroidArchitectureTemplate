package pl.jsyty.architecturetemplate.libraries.mapstructspi

import com.google.auto.service.AutoService
import org.mapstruct.ap.spi.AccessorNamingStrategy
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy
import java.util.Locale
import javax.lang.model.element.ExecutableElement

/**
 * Custom accessor naming strategy that allows us to bypass some of the issues with mapstruct-kotlin incompatibility
 * Solved issues:
 * - need to explicitly ignore "copy" when object has a single field
 * - no way to use property which name starts with "is" or "has"
 *
 * For original naming strategy for at: https://github.com/mapstruct/mapstruct/blob/master/processor/src/main/java/org/mapstruct/ap/spi/DefaultAccessorNamingStrategy.java
 */
@Suppress("unused")
@AutoService(AccessorNamingStrategy::class)
class CustomAccessorNamingStrategy : DefaultAccessorNamingStrategy() {
    override fun isSetterMethod(method: ExecutableElement): Boolean {
        val methodName = method.simpleName.toString()
        // allows us to use data classes with only single fields
        return methodName.startsWith("set") && methodName.length > 3
    }

    override fun isPresenceCheckMethod(method: ExecutableElement): Boolean {
        // allows us to use properties starting with "has"
        return false
    }

    override fun getPropertyName(getterOrSetterMethod: ExecutableElement): String {
        val methodName = getterOrSetterMethod.simpleName.toString()
        return if (methodName.startsWith("get") || methodName.startsWith("set")) {
            methodName.substring(3).replaceFirstChar { it.lowercase(Locale.getDefault()) }
        } else {
            // scenario with "is" and "has" - do not change the name here so we can treat those according to the kotlin standards
            methodName
        }
    }
}
