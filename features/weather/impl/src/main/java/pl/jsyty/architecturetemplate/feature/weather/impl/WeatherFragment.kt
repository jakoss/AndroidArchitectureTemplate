package pl.jsyty.architecturetemplate.feature.weather.impl

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deliveryhero.whetstone.fragment.ContributesFragment
import org.orbitmvi.orbit.compose.collectAsState
import pl.jsyty.architecturetemplate.feature.weather.WeatherDirection
import pl.jsyty.architecturetemplate.infrastructure.extensions.localisedDateTime
import pl.jsyty.architecturetemplate.ui.BaseDirectableComposeFragment
import pl.jsyty.architecturetemplate.ui.async.FullscreenAsyncHandler
import pl.jsyty.architecturetemplate.ui.composeViewModel
import javax.inject.Inject

@ContributesFragment
class WeatherFragment @Inject constructor() : BaseDirectableComposeFragment<WeatherDirection>() {
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val viewModel = composeViewModel<WeatherViewModel>()
            val state by viewModel.collectAsState()

            FullscreenAsyncHandler(
                state = state.currentWeather,
                onRetryAction = { viewModel.initialize() }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Temperature: ${it.temperature}")
                    Text(text = "Windspeed: ${it.windspeed}")
                    Text(
                        text = "Time: ${it.time.localisedDateTime()}"
                    )
                }
            }

            LaunchedEffect(Unit) {
                viewModel.initialize()
            }
        }
    }
}
