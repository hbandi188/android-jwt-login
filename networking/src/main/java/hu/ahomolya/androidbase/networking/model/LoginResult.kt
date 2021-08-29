package hu.ahomolya.androidbase.networking.model

import hu.ahomolya.androidbase.networking.model.internal.TokenResponse

sealed class LoginResult {
    data class Success(val tokens: TokenResponse) : LoginResult()
}
