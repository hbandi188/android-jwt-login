package hu.ahomolya.androidbase.networking.contract

/**
 * A interface that can be queried for application secrets.
 */
interface SecretsProvider {
    /**
     * Get the client id used to communicate with the backend.
     */
    fun getClientId(): String
}
