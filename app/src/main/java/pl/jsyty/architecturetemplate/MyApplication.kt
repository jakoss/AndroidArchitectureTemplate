package pl.jsyty.architecturetemplate

import android.app.Application
import pl.jsyty.architecturetemplate.infrastructure.di.ComponentHolder
import pl.jsyty.architecturetemplate.infrastructure.navigation.DirectionAggregator
import pl.jsyty.architecturetemplate.test.StartDirection
import pl.jsyty.architecturetemplate.test.TestFragment
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        // TODO : just temporary navigation registration site
        DirectionAggregator.registerDirection<StartDirection> { TestFragment() }

        ComponentHolder.components += DaggerAppComponent.create()
    }
}
