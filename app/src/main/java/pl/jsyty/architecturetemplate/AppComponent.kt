package pl.jsyty.architecturetemplate

import com.squareup.anvil.annotations.MergeComponent
import pl.jsyty.architecturetemplate.infrastructure.di.AppScope
import javax.inject.Singleton

@Singleton
@MergeComponent(AppScope::class)
interface AppComponent