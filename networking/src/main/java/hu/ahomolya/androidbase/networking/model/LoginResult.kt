package hu.ahomolya.androidbase.networking.model

import hu.ahomolya.androidbase.networking.model.internal.TokenResponse

sealed class LoginResult {
    /**
     * Denotes a successful login attempt, [tokens] is the response.
     */
    data class Success(val tokens: TokenResponse) : LoginResult()

    /**
     * Denotes a generic network exception, [cause] is the cause.
     */
    class NetworkError(val cause: Throwable) : LoginResult()

    /**
     * Denotes an HTTP error indicated by [code].
     */
    data class HttpError(val code: Int) : LoginResult()

    /**
     * Specifically represents 401 errors.
     */
    object Unauthorized : LoginResult()
}
