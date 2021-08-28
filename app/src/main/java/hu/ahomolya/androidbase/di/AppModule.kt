package hu.ahomolya.androidbase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.ahomolya.androidbase.di.DiQualifiers.NavigationChannel
import hu.ahomolya.androidbase.model.NavigationCommand
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import hu.ahomolya.androidbase.provider.SecretsProviderImpl
import kotlinx.coroutines.channels.Channel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @NavigationChannel
    @Provides
    @Singleton
    fun provideNavigationChannel() = Channel<NavigationCommand>()

    @Provides
    fun provideSecretsProvider(): SecretsProvider = SecretsProviderImpl()
}