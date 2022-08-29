package pl.jsyty.architecturetemplate.core.networking

import android.app.Application
import com.chimerapps.niddler.core.AndroidNiddler

/**
 * Manages [AndroidNiddler] initialization and lifecycle
 *
 * Stores internal [niddler] instance for interceptor and retrofit plugin setup
 */
object NiddlerHandler {
    internal lateinit var niddler: AndroidNiddler

    fun init(app: Application) {
        niddler = AndroidNiddler.Builder()
            .setPort(0)
            .setNiddlerInformation(AndroidNiddler.fromApplication(app))
            .setMaxStackTraceSize(50)
            .build()
    }

    fun start() {
        niddler.start()
    }

    fun stop() {
        niddler.close()
    }
}
