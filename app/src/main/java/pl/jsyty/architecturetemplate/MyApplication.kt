package pl.jsyty.architecturetemplate

import android.app.Application
import pl.jsyty.architecturetemplate.feature.dashboard.impl.DashboardModule
import pl.jsyty.architecturetemplate.infrastructure.di.ComponentHolder
import tangle.inject.TangleGraph
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        // TODO : only a temporary solution to register feature modules
        DashboardModule.register()

        val appComponent = DaggerAppComponent.factory().create(this)
        ComponentHolder.components += appComponent
        TangleGraph.add(appComponent)
    }
}
