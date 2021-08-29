package hu.ahomolya.androidbase.networking.service.impl

import hu.ahomolya.androidbase.networking.api.LoginApi
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.networking.service.LoginService

internal class LoginServiceImpl(
    private val loginApi: LoginApi,
    private val secretsProvider: SecretsProvider,
) : LoginService {
    override suspend fun login(username: String, password: String): LoginResult =
        LoginResult.Success(loginApi.login(username, password, "password", secretsProvider.getClientId()))
}