package hu.ahomolya.androidbase.networking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import hu.ahomolya.androidbase.networking.service.LoginService
import hu.ahomolya.androidbase.networking.service.impl.LoginServiceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
internal object NetworkingModule {
    @Provides
    fun provideLoginService(): LoginService = LoginServiceImpl()
}