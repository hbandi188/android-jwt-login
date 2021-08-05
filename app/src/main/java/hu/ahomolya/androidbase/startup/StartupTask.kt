package hu.ahomolya.androidbase.startup

/**
 * Represents a single task that is run on startup.
 */
interface StartupTask {
    /**
     * Can be overridden to give priority to certain tasks. Lower priority gets run sooner.
     */
    val priority: Int get() = 0

    /**
     * Execute this task.
     */
    fun execute()
}
