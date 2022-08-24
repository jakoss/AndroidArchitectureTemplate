package pl.jsyty.architecturetemplate.core.networking

import okhttp3.OkHttpClient

/**
 * This singleton is used to setup additional interceptors **before** DI container setup
 */
object OkHttpBuilderSteps {
    private val builderSteps = mutableListOf<OkHttpBuilderStep>()

    @Suppress("Unused")
    fun addBuilder(okHttpBuilder: OkHttpBuilderStep) {
        builderSteps.add(okHttpBuilder)
    }

    internal fun applySteps(builder: OkHttpClient.Builder) {
        for (step in builderSteps) {
            step.addBuildStep(builder)
        }
    }
}

interface OkHttpBuilderStep {
    fun addBuildStep(builder: OkHttpClient.Builder)
}
