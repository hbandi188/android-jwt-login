package hu.ahomolya.androidbase

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import hu.ahomolya.androidbase.startup.StartupExecutor
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var startupExecutor: StartupExecutor

    override fun onCreate() {
        super.onCreate()
        startupExecutor.execute()
    }
}