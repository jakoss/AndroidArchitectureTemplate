package pl.jsyty.architecturetemplate.core.networking

import android.content.Context
import com.chimerapps.niddler.interceptor.okhttp.NiddlerOkHttpInterceptor
import com.deliveryhero.whetstone.ForScope
import com.deliveryhero.whetstone.app.ApplicationScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.*
import pl.jsyty.architecturetemplate.core.networking.interceptors.NoContentInterceptor
import pl.jsyty.architecturetemplate.core.networking.interceptors.RetryInterceptor
import java.io.File
import java.time.Duration
import javax.inject.Singleton

@Suppress("Unused")
@Module
@ContributesTo(ApplicationScope::class)
object NetworkingModule {

    /**
     * Provide basic [OkHttpClient] setup complete with cache, timeout and all interceptors
     */
    @Singleton
    @Provides
    @Suppress("MagicNumber")
    fun provideOkHttpClient(@ForScope(ApplicationScope::class) context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(
                Cache(
                    File(context.cacheDir, "api_cache"),
                    50L * 1024 * 1024
                )
            ) // setup 50 MB of api cache
            .connectTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(15))
            .writeTimeout(Duration.ofSeconds(30))
            .addInterceptor(NoContentInterceptor())
            .addInterceptor(NiddlerOkHttpInterceptor(NiddlerHandler.niddler, "Niddler", true))

        OkHttpBuilderSteps.applySteps(builder)

        return builder
            .addInterceptor(RetryInterceptor())
            .build()
    }

    /**
     * Provide basic [Json] setup that will be shared accross the application
     */
    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
}
