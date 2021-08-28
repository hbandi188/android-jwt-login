package hu.ahomolya.androidbase.networking.service

/**
 * Service for logging in.
 */
interface LoginService {
    /**
     * Start the login process with the provided [username] and [password].
     */
    fun login(username: String, password: String)
}