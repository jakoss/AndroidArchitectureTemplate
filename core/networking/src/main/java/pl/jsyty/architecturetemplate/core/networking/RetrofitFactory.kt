package pl.jsyty.architecturetemplate.core.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import pl.jsyty.architecturetemplate.infrastructure.BuildInformation
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitFactory @Inject constructor(
    private val httpClient: OkHttpClient,
    private val buildInformation: BuildInformation,
    private val json: Json,
) {
    @OptIn(ExperimentalSerializationApi::class)
    fun <T> create(baseUrl: String, serviceType: Class<T>): T {
        val builder = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(buildInformation.apiUrl + "/$baseUrl/")
        return builder.build().create(serviceType)
    }

    inline fun <reified T> create(baseUrl: String): T {
        return create(baseUrl, T::class.java)
    }
}
