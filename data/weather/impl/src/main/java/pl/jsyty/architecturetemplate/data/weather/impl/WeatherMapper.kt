package pl.jsyty.architecturetemplate.data.weather.impl

import com.syouth.kmapper.processor_annotations.Mapper
import pl.jsyty.architecturetemplate.data.weather.CurrentWeather
import pl.jsyty.architecturetemplate.infrastructure.mapping.DateTimeConverters

@Mapper
interface WeatherMapper: DateTimeConverters {
    fun fromDto(weatherDto: CurrentWeatherDto): CurrentWeather
}
