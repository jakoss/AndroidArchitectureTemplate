package pl.jsyty.architecturetemplate.core.networking.interceptors

import io.github.resilience4j.kotlin.retry.RetryConfig
import io.github.resilience4j.kotlin.retry.RetryRegistry
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Retries all failed requests with exponential backoff
 */
class RetryInterceptor : Interceptor {
    private val retryRegistry = RetryRegistry {
        withRetryConfig(
            RetryConfig<Response> {
                maxAttempts(3)
                retryExceptions(IOException::class.java)
                retryOnResult { it.code in 502..504 }
                failAfterMaxAttempts(true)
                intervalFunction { numOfAttempts -> numOfAttempts * 1000L }
            }
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null

        val retry = retryRegistry.retry("requestRetry")
        return retry.executeCheckedSupplier {
            // close any possible previous responses (fixes a leak exception)
            response?.close()

            val localResponse = chain.proceed(request)
            response = localResponse
            localResponse
        }
    }
}
