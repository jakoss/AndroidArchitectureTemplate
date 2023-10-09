package pl.jsyty.architecturetemplate

import com.deliveryhero.whetstone.app.ApplicationScope
import com.squareup.anvil.annotations.ContributesBinding
import pl.jsyty.architecturetemplate.infrastructure.BuildInformation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ContributesBinding(scope = ApplicationScope::class)
class BuildInformationImpl @Inject constructor() : BuildInformation {
    override val apiUrl: String = BuildConfig.API_URL
}
