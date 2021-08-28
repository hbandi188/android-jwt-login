package hu.ahomolya.androidbase.networking.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.ahomolya.androidbase.networking.api.LoginApi
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import hu.ahomolya.androidbase.networking.service.LoginService
import hu.ahomolya.androidbase.networking.service.impl.LoginServiceImpl
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkingModule {
    /**
     * Not making this part of regular DI to avoid Hilt trying to parse it as a binding in the App module.
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://example.vividmindsoft.com")
        .build()

    @Provides
    fun provideLoginService(loginApi: LoginApi, secretsProvider: SecretsProvider): LoginService =
        LoginServiceImpl(loginApi, secretsProvider)

    @Provides
    fun provideLoginApi(): LoginApi = retrofit.create(LoginApi::class.java)
}