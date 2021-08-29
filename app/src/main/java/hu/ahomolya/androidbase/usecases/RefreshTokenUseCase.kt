package hu.ahomolya.androidbase.usecases

/**
 * A use case that checks whether there is a refresh token present in the preferences store and attempts to refresh the access token with
 * it. Will automatically navigate to the home screen if successful.
 */
interface RefreshTokenUseCase {
    /**
     * Launch this use case.
     */
    suspend fun attemptRefresh()
}
