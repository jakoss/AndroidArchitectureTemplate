package pl.jsyty.architecturetemplate.data.weather.impl

import com.deliveryhero.whetstone.app.ApplicationScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import pl.jsyty.architecturetemplate.core.networking.RetrofitFactory
import javax.inject.Singleton

@Suppress("Unused")
@Module
@ContributesTo(ApplicationScope::class)
object WeatherDataModule {
    @Singleton
    @Provides
    fun provideWeatherService(retrofitFactory: RetrofitFactory): WeatherService {
        return retrofitFactory.create("forecast")
    }
}
