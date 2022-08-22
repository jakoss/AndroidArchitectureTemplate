package pl.jsyty.architecturetemplate.core.networking.interceptors

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Treats 200 as proper successful response
 */
class NoContentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        return if (response.code == 204) {
            // build empty body for 204 reponses (just to keep retrofit happy about the result)
            response.newBuilder()
                .code(200)
                .body(
                    "{}".toResponseBody("application/json".toMediaType())
                ).build()
        } else {
            response
        }
    }
}
