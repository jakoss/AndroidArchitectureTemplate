package pl.jsyty.architecturetemplate.data.weather.impl

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import pl.jsyty.architecturetemplate.core.networking.RetrofitFactory
import pl.jsyty.architecturetemplate.infrastructure.di.AppScope
import pl.jsyty.architecturetemplate.infrastructure.mapping.getMapper
import javax.inject.Singleton

@Suppress("Unused")
@Module
@ContributesTo(AppScope::class)
object WeatherDataModule {
    @Singleton
    @Provides
    fun provideWeatherService(retrofitFactory: RetrofitFactory): WeatherService {
        return retrofitFactory.create("forecast")
    }

    @Singleton
    @Provides
    fun providesWeatherMapper(): WeatherMapper = getMapper()
}
