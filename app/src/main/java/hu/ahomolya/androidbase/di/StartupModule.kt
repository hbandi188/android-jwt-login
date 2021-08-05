package hu.ahomolya.androidbase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.ahomolya.androidbase.startup.StartupExecutor
import hu.ahomolya.androidbase.startup.impl.StartupExecutorImpl
import hu.ahomolya.androidbase.startup.tasks.SetupLoggingTask

@Module
@InstallIn(SingletonComponent::class)
object StartupModule {
    @Provides
    fun provideStartupExecutor(
        setupLoggingTask: SetupLoggingTask,
    ): StartupExecutor = StartupExecutorImpl(
        listOf(
            setupLoggingTask,
        ),
    )

    @Provides
    fun provideSetupLoggingTask() = SetupLoggingTask()
}