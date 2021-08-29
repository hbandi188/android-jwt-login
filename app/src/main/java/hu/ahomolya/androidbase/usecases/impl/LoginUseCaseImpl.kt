package hu.ahomolya.androidbase.usecases.impl

import hu.ahomolya.androidbase.R
import hu.ahomolya.androidbase.model.SavedTokens
import hu.ahomolya.androidbase.navigation.NavigationDispatcher
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.networking.service.LoginService
import hu.ahomolya.androidbase.store.PreferencesStore
import hu.ahomolya.androidbase.usecases.LoginUseCase
import java.time.Clock

class LoginUseCaseImpl(
    private val loginService: LoginService,
    private val preferencesStore: PreferencesStore,
    private val clock: Clock,
    private val navigationDispatcher: NavigationDispatcher,
) : LoginUseCase {
    override suspend fun login(username: String, password: String): LoginResult {
        val loginResult = loginService.login(username, password)

        if (loginResult is LoginResult.Success) {
            preferencesStore.tokens =
                SavedTokens(
                    accessToken = loginResult.tokens.accessToken,
                    refreshToken = loginResult.tokens.refreshToken,
                    validUntil = clock.instant().plusSeconds(loginResult.tokens.expiresIn.toLong()),
                )
            navigationDispatcher.dispatch(R.id.goToHomeScreen)
        }

        return loginResult
    }
}
