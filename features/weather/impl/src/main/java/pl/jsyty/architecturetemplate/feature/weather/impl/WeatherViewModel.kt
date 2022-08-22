package pl.jsyty.architecturetemplate.feature.weather.impl

import org.orbitmvi.orbit.syntax.simple.intent
import pl.jsyty.architecturetemplate.data.weather.CurrentWeather
import pl.jsyty.architecturetemplate.data.weather.WeatherRepository
import pl.jsyty.architecturetemplate.infrastructure.async.Async
import pl.jsyty.architecturetemplate.infrastructure.async.Uninitialized
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.BaseViewModel
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.async
import tangle.viewmodel.VMInject

class WeatherViewModel @VMInject constructor(private val weatherRepository: WeatherRepository) :
    BaseViewModel<WeatherViewModel.State, Unit>(State()) {
    data class State(
        val currentWeather: Async<CurrentWeather> = Uninitialized,
    )

    fun initialize() = intent {
        async {
            weatherRepository.getCurrentWeather(52.2297, 21.0122)
        }.execute { state.copy(currentWeather = it) }
    }
}
