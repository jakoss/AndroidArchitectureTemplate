package pl.jsyty.architecturetemplate

import com.squareup.anvil.annotations.ContributesBinding
import pl.jsyty.architecturetemplate.infrastructure.BuildInformation
import pl.jsyty.architecturetemplate.infrastructure.di.AppScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(scope = AppScope::class)
class BuildInformationImpl @Inject constructor() : BuildInformation {
    override val apiUrl: String = BuildConfig.API_URL
}
