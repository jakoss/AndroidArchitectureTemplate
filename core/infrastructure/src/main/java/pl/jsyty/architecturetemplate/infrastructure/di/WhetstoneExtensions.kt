package pl.jsyty.architecturetemplate.infrastructure.di

import android.app.Application
import android.content.Context
import com.deliveryhero.whetstone.Whetstone

inline fun <reified T : Any> Context.getApplicationComponent(): T {
    val application = this.applicationContext as Application
    return Whetstone.fromApplication(application)
}
