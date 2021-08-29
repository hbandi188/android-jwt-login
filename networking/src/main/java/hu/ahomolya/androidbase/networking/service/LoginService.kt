package hu.ahomolya.androidbase.networking.service

import hu.ahomolya.androidbase.networking.model.LoginResult

/**
 * Service for logging in.
 */
interface LoginService {
    /**
     * Start the login process with the provided [username] and [password]. Returns the result of the login operation.
     */
    suspend fun login(username: String, password: String): LoginResult

    /**
     * Refresh the user's session with the provided refresh token.
     */
    suspend fun refresh(refreshToken: String): LoginResult
}