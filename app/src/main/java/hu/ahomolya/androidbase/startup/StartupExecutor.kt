package hu.ahomolya.androidbase.startup

/**
 * Runs tasks on application startup.
 */
interface StartupExecutor {
    fun execute()
}