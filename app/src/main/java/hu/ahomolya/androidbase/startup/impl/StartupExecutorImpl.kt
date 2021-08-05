package hu.ahomolya.androidbase.startup.impl

import hu.ahomolya.androidbase.startup.StartupExecutor
import hu.ahomolya.androidbase.startup.StartupTask

class StartupExecutorImpl(private val tasks: List<StartupTask>) : StartupExecutor {
    override fun execute() {
        tasks
            .sortedBy { it.priority }
            .forEach { it.execute() }
    }
}