package hu.ahomolya.androidbase.ui.login

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import hu.ahomolya.androidbase.ui.login.impl.LoginViewModelImpl
import hu.ahomolya.androidbase.usecases.LoginUseCase
import hu.ahomolya.androidbase.usecases.RefreshTokenUseCase

@Module
@InstallIn(ActivityRetainedComponent::class)
object LoginModule {
    fun provideViewModel(loginUseCase: LoginUseCase, refreshTokenUseCase: RefreshTokenUseCase): LoginViewModelImpl =
        LoginViewModelImpl(loginUseCase, refreshTokenUseCase)
}