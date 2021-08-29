package hu.ahomolya.androidbase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import hu.ahomolya.androidbase.navigation.NavigationDispatcher
import hu.ahomolya.androidbase.networking.service.LoginService
import hu.ahomolya.androidbase.store.PreferencesStore
import hu.ahomolya.androidbase.usecases.LoginUseCase
import hu.ahomolya.androidbase.usecases.RefreshTokenUseCase
import hu.ahomolya.androidbase.usecases.impl.LoginUseCaseImpl
import hu.ahomolya.androidbase.usecases.impl.RefreshTokenUseCaseImpl
import java.time.Clock

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {
    @Provides
    fun provideLoginUseCase(
        loginService: LoginService,
        preferencesStore: PreferencesStore,
        clock: Clock,
        navigationDispatcher: NavigationDispatcher,
    ): LoginUseCase =
        LoginUseCaseImpl(loginService, preferencesStore, clock, navigationDispatcher)

    @Provides
    fun provideRefreshTokenUseCase(
        preferencesStore: PreferencesStore,
        loginService: LoginService,
        clock: Clock,
        navigationDispatcher: NavigationDispatcher,
    ): RefreshTokenUseCase = RefreshTokenUseCaseImpl(preferencesStore, loginService, clock, navigationDispatcher)
}