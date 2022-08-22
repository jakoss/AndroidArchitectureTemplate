package pl.jsyty.architecturetemplate.data.weather.impl

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET(".")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean,
        @Query("timezone") timezone: String,
    ): WeatherDto
}
