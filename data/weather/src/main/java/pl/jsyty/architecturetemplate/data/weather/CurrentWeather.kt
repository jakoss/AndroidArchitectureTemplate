package pl.jsyty.architecturetemplate.data.weather

import java.time.LocalDateTime

data class CurrentWeather(
    val temperature: Double,
    val windspeed: Double,
    val time: LocalDateTime,
)
