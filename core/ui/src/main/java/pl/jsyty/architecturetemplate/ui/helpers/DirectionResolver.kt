//ktlint-disable filename
package pl.jsyty.architecturetemplate.ui.helpers

import android.os.Build
import androidx.fragment.app.Fragment
import pl.jsyty.architecturetemplate.infrastructure.navigation.ARGUMENT_KEY
import pl.jsyty.architecturetemplate.infrastructure.navigation.Direction

@Suppress("unused")
inline fun <reified T: Direction> Fragment.direction(): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(ARGUMENT_KEY, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        arguments?.getParcelable(ARGUMENT_KEY)
    } ?: error("No argument found")
}
