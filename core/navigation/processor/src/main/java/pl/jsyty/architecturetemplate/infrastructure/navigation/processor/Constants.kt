package pl.jsyty.architecturetemplate.infrastructure.navigation.processor

import com.squareup.kotlinpoet.ClassName
import org.jetbrains.kotlin.name.FqName

object FqNames {
    val direction =
        FqName("pl.jsyty.architecturetemplate.infrastructure.navigation.Direction")
    val baseDirectableComposeFragment = FqName("pl.jsyty.architecturetemplate.ui.BaseDirectableComposeFragment")
    val baseDirectableComposeDialogFragment = FqName("pl.jsyty.architecturetemplate.ui.BaseDirectableComposeDialogFragment")
    val androidFragment = FqName("androidx.fragment.app.Fragment")
}

object ClassNames {
    val appScope = ClassName("pl.jsyty.architecturetemplate.infrastructure.di", "AppScope")
    val androidFragment = ClassName("androidx.fragment.app", "Fragment")
    val injectAnnotation = ClassName("javax.inject", "Inject")
    val directionKey = ClassName("pl.jsyty.architecturetemplate.infrastructure.navigation", "DirectionKey")
    val fragmentFactory = ClassName("pl.jsyty.architecturetemplate.infrastructure.navigation", "FragmentFactory")
}
