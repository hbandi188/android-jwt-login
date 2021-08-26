package hu.ahomolya.androidbase.ui.login

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import hu.ahomolya.androidbase.ui.login.impl.LoginViewModelImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
object LoginModule {
    fun provideViewModel(): LoginViewModelImpl = LoginViewModelImpl()
}