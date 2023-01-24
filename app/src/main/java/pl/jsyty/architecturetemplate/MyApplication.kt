package pl.jsyty.architecturetemplate

import android.app.Application
import com.deliveryhero.whetstone.Whetstone
import com.deliveryhero.whetstone.app.*
import pl.jsyty.architecturetemplate.core.networking.NiddlerHandler
import timber.log.Timber

@ContributesAppInjector(generateAppComponent = true)
open class MyApplication : Application(), ApplicationComponentOwner {
    override fun onCreate() {
        setupPreInjection()
        Whetstone.inject(this)
        super.onCreate()

        @Suppress("KotlinConstantConditions")
        if (BuildConfig.BUILD_TYPE != "release") {
            Timber.plant(Timber.DebugTree())
        }
        NiddlerHandler.init(this)
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

    override val applicationComponent by lazy {
        GeneratedApplicationComponent.create(this)
    }
}
