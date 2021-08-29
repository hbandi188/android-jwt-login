package hu.ahomolya.androidbase.usecases

import hu.ahomolya.androidbase.networking.model.LoginResult

/**
 * Use case for logging the user in.
 */
interface LoginUseCase {
    /**
     * Login using the provided [username] and [password]. If successful, will move to the next screen.
     */
    suspend fun login(username: String, password: String): LoginResult
}