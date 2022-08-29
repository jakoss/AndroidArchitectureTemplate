package pl.jsyty.architecturetemplate

import com.pluto.Pluto
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.logger.PlutoTimberTree
import com.pluto.plugins.network.PlutoInterceptor
import com.pluto.plugins.network.PlutoNetworkPlugin
import okhttp3.OkHttpClient
import pl.jsyty.architecturetemplate.core.networking.OkHttpBuilderStep
import pl.jsyty.architecturetemplate.core.networking.OkHttpBuilderSteps
import timber.log.Timber

@Suppress("unused")
class DebugApplication : MyApplication() {
    override fun setupPreInjection() {
        OkHttpBuilderSteps.addBuilder(object : OkHttpBuilderStep {
            override fun addBuildStep(builder: OkHttpClient.Builder) {
                builder.addInterceptor(PlutoInterceptor())
            }
        })
    }

    override fun onCreate() {
        super.onCreate()

        Pluto.Installer(this)
            .addPlugin(PlutoLoggerPlugin("logger"))
            .addPlugin(PlutoExceptionsPlugin("exceptions"))
            .addPlugin(PlutoNetworkPlugin("network"))
            .install()

        Timber.plant(PlutoTimberTree())
    }
}
