package hu.ahomolya.androidbase.usecases.impl

import hu.ahomolya.androidbase.R
import hu.ahomolya.androidbase.model.SavedTokens
import hu.ahomolya.androidbase.navigation.NavigationDispatcher
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.networking.service.LoginService
import hu.ahomolya.androidbase.store.PreferencesStore
import hu.ahomolya.androidbase.usecases.RefreshTokenUseCase
import java.time.Clock

class RefreshTokenUseCaseImpl(
    private val preferencesStore: PreferencesStore,
    private val loginService: LoginService,
    private val clock: Clock,
    private val navigationDispatcher: NavigationDispatcher,
) : RefreshTokenUseCase {
    override suspend fun attemptRefresh() {
        val tokens = preferencesStore.tokens
        if (tokens != null) {
            val result = loginService.refresh(tokens.refreshToken)
            if (result is LoginResult.Success) {
                preferencesStore.tokens = SavedTokens(
                    accessToken = result.tokens.accessToken,
                    refreshToken = result.tokens.refreshToken,
                    validUntil = clock.instant().plusSeconds(result.tokens.expiresIn.toLong()),
                )

                navigationDispatcher.dispatch(R.id.goToHomeScreen)
            }
        }
    }
}