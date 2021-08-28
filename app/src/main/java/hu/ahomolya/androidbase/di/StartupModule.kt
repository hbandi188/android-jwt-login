package hu.ahomolya.androidbase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.ahomolya.androidbase.startup.StartupExecutor
import hu.ahomolya.androidbase.startup.impl.StartupExecutorImpl
import hu.ahomolya.androidbase.startup.tasks.LoadNativeLibraryTask
import hu.ahomolya.androidbase.startup.tasks.SetupLoggingTask

@Module
@InstallIn(SingletonComponent::class)
object StartupModule {
    @Provides
    fun provideStartupExecutor(
        setupLoggingTask: SetupLoggingTask,
        loadNativeLibraryTask: LoadNativeLibraryTask,
    ): StartupExecutor = StartupExecutorImpl(
        listOf(
            setupLoggingTask,
            loadNativeLibraryTask,
        ),
    )

    @Provides
    fun provideSetupLoggingTask() = SetupLoggingTask()

    @Provides
    fun provideLoadNativeLibraryTask() = LoadNativeLibraryTask()
}