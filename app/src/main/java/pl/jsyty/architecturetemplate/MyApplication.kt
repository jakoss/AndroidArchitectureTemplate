package pl.jsyty.architecturetemplate

import android.app.Application
import pl.jsyty.architecturetemplate.core.networking.NiddlerHandler
import pl.jsyty.architecturetemplate.infrastructure.di.ComponentHolder
import tangle.inject.TangleGraph
import timber.log.Timber

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        @Suppress("KotlinConstantConditions")
        if (BuildConfig.BUILD_TYPE != "release") {
            Timber.plant(Timber.DebugTree())
        }
        NiddlerHandler.init(this)

        setupPreInjection()
        val appComponent = DaggerAppComponent.factory().create(this)
        ComponentHolder.components += appComponent
        TangleGraph.add(appComponent)

        NiddlerHandler.start()
    }

    override fun onTerminate() {
        NiddlerHandler.stop()

        super.onTerminate()
    }

    /**
     * Inheriting class can override this function to make some custom initialization.
     * This is called BEFORE Dagger container is setup.
     */
    protected open fun setupPreInjection() {
        // nothing here, only for override
    }
}
