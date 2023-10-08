package pl.jsyty.architecturetemplate.feature.weather.impl

import com.deliveryhero.whetstone.viewmodel.ContributesViewModel
import org.orbitmvi.orbit.syntax.simple.intent
import pl.jsyty.architecturetemplate.data.weather.CurrentWeather
import pl.jsyty.architecturetemplate.data.weather.WeatherRepository
import pl.jsyty.architecturetemplate.infrastructure.async.Async
import pl.jsyty.architecturetemplate.infrastructure.async.Uninitialized
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.BaseViewModel
import pl.jsyty.architecturetemplate.infrastructure.viewmodel.async
import javax.inject.Inject

@ContributesViewModel
class WeatherViewModel
    @Inject
    constructor(private val weatherRepository: WeatherRepository) :
    BaseViewModel<WeatherViewModel.State, Unit>(State()) {
        data class State(
            val currentWeather: Async<CurrentWeather> = Uninitialized,
        )

        fun initialize() =
            intent {
                async {
                    @Suppress("MagicNumber")
                    weatherRepository.getCurrentWeather(52.2297, 21.0122)
                }.execute { state.copy(currentWeather = it) }
            }
    }
