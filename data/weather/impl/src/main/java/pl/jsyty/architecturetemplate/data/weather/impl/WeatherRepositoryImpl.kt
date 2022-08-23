package pl.jsyty.architecturetemplate.data.weather.impl

import com.squareup.anvil.annotations.ContributesBinding
import pl.jsyty.architecturetemplate.data.weather.CurrentWeather
import pl.jsyty.architecturetemplate.data.weather.WeatherRepository
import pl.jsyty.architecturetemplate.infrastructure.di.AppScope
import javax.inject.Inject

@ContributesBinding(scope = AppScope::class)
class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherMapper: WeatherMapper,
) : WeatherRepository {
    override suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeather {
        val weather =
            weatherService.getWeather(latitude, longitude, currentWeather = true, timezone = "UTC")

        return weatherMapper.fromDto(weather.currentWeather)
    }
}
