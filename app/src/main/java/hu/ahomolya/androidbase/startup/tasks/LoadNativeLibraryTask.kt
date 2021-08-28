package hu.ahomolya.androidbase.startup.tasks

import hu.ahomolya.androidbase.startup.StartupTask

class LoadNativeLibraryTask : StartupTask {
    override fun execute() {
        System.loadLibrary("native-lib")
    }
}