package pl.jsyty.architecturetemplate.data.weather

interface WeatherRepository {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double): CurrentWeather
}
