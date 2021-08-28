package hu.ahomolya.androidbase.networking.service.impl

import hu.ahomolya.androidbase.networking.api.LoginApi
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import hu.ahomolya.androidbase.networking.service.LoginService

internal class LoginServiceImpl(
    private val loginApi: LoginApi,
    private val secretsProvider: SecretsProvider,
) : LoginService {
    override fun login(username: String, password: String) {
        loginApi.login(username, password, "password", secretsProvider.getClientId())
    }
}