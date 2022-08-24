package pl.jsyty.architecturetemplate.infrastructure.di

import android.app.Application
import android.content.Context
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Qualifier
annotation class ApplicationContext

@Suppress("Unused")
@Module
@ContributesTo(AppScope::class)
interface ContextModule {
    @get:Binds
    @get:ApplicationContext
    val Application.bindContext: Context
}
