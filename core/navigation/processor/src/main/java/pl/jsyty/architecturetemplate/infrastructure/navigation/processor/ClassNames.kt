package pl.jsyty.architecturetemplate.infrastructure.navigation.processor

import com.squareup.kotlinpoet.ClassName

val appScopeClassName = ClassName("pl.jsyty.architecturetemplate.infrastructure.di", "AppScope")
val directionKeyClassName = ClassName("pl.jsyty.architecturetemplate.infrastructure.navigation", "DirectionKey")
val fragmentFactoryClassName = ClassName("pl.jsyty.architecturetemplate.infrastructure.navigation", "FragmentFactory")
val androidFragmentClassName = ClassName("androidx.fragment.app", "Fragment")
val injectAnnotationClassName = ClassName("javax.inject", "Inject")
