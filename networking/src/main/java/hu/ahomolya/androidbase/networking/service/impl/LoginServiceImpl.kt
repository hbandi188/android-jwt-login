package hu.ahomolya.androidbase.networking.service.impl

import hu.ahomolya.androidbase.networking.api.LoginApi
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.networking.service.LoginService
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

internal class LoginServiceImpl(
    private val loginApi: LoginApi,
    private val secretsProvider: SecretsProvider,
) : LoginService {
    override suspend fun login(username: String, password: String): LoginResult =
        kotlin.runCatching { LoginResult.Success(loginApi.login(username, password, "password", secretsProvider.getClientId())) }
            .getOrElse { throwable ->
                when (throwable) {
                    is HttpException -> when (throwable.code()) {
                        HTTP_UNAUTHORIZED -> LoginResult.Unauthorized
                        else -> LoginResult.HttpError(throwable.code())
                    }
                    else -> LoginResult.NetworkError(throwable)
                }
            }

    override suspend fun refresh(refreshToken: String): LoginResult =
        kotlin.runCatching { LoginResult.Success(loginApi.refresh(refreshToken, "refresh_token", secretsProvider.getClientId())) }
            .getOrElse { throwable ->
                when (throwable) {
                    is HttpException -> when (throwable.code()) {
                        HTTP_UNAUTHORIZED -> LoginResult.Unauthorized
                        else -> LoginResult.HttpError(throwable.code())
                    }
                    else -> LoginResult.NetworkError(throwable)
                }
            }

}