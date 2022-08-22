package pl.jsyty.architecturetemplate.data.weather.impl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.jsyty.architecturetemplate.core.networking.serializers.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class CurrentWeatherDto(
    val temperature: Double,
    val windspeed: Double,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val time: OffsetDateTime,
)

@Serializable
data class WeatherDto(
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherDto,
)
