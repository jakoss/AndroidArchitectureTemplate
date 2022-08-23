package pl.jsyty.architecturetemplate.data.weather.impl

import org.mapstruct.Mapper
import pl.jsyty.architecturetemplate.data.weather.CurrentWeather
import pl.jsyty.architecturetemplate.infrastructure.mapping.DateConversionMapper

@Mapper(uses = [DateConversionMapper::class])
interface WeatherMapper {
    fun fromDto(weatherDto: CurrentWeatherDto): CurrentWeather
}
