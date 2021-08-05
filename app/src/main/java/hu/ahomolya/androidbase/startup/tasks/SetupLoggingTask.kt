package hu.ahomolya.androidbase.startup.tasks

import hu.ahomolya.androidbase.BuildConfig
import hu.ahomolya.androidbase.startup.StartupTask
import timber.log.Timber
import javax.inject.Inject

/**
 * Sets up Timber on app start.
 */
class SetupLoggingTask @Inject constructor() : StartupTask {
    override val priority: Int = Int.MIN_VALUE

    override fun execute() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}