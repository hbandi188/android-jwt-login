package hu.ahomolya.androidbase.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.ahomolya.androidbase.di.DiQualifiers.NavigationChannel
import hu.ahomolya.androidbase.model.NavigationCommand
import hu.ahomolya.androidbase.navigation.NavigationDispatcher
import hu.ahomolya.androidbase.navigation.impl.NavigationDispatcherImpl
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import hu.ahomolya.androidbase.provider.SecretsProviderImpl
import hu.ahomolya.androidbase.store.PreferencesStore
import hu.ahomolya.androidbase.store.impl.PreferencesStoreImpl
import kotlinx.coroutines.channels.Channel
import java.time.Clock
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

    @Singleton
    @Provides
    fun providePreferencesStore(sharedPreferences: SharedPreferences): PreferencesStore = PreferencesStoreImpl(sharedPreferences)

    @Provides
    fun provideNavigationDispatcher(
        @NavigationChannel channel: Channel<NavigationCommand>,
    ): NavigationDispatcher = NavigationDispatcherImpl(channel)

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    @Provides
    fun provideClock(): Clock = Clock.systemDefaultZone()
}