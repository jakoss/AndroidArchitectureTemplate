package pl.jsyty.architecturetemplate.data.weather.impl

import io.mcarle.konvert.api.Konverter
import pl.jsyty.architecturetemplate.data.weather.CurrentWeather
import pl.jsyty.architecturetemplate.infrastructure.mapping.DateTimeConverters

@Konverter
interface WeatherMapper : DateTimeConverters {
    fun fromDto(weatherDto: CurrentWeatherDto): CurrentWeather
    fun toDto(weather: CurrentWeather): CurrentWeatherDto
}
