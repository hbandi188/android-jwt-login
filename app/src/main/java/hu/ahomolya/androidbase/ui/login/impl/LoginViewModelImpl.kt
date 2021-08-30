package hu.ahomolya.androidbase.ui.login.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.ahomolya.androidbase.R
import hu.ahomolya.androidbase.contract.DialogEventSource.DialogEvent
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.ui.login.LoginViewModel
import hu.ahomolya.androidbase.usecases.LoginUseCase
import hu.ahomolya.androidbase.usecases.RefreshTokenUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) : ViewModel(), LoginViewModel {
    override val startupJob: Job
    override val username: MutableStateFlow<String> = MutableStateFlow("")
    override val password: MutableStateFlow<String> = MutableStateFlow("")
    override val enableLogin: StateFlow<Boolean> = combine(username, password) { un, pw -> un.isNotBlank() && pw.isNotBlank() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
    override val loginInProgress = MutableStateFlow(false)
    override val dialogEvents = Channel<DialogEvent>()

    init {
        startupJob = viewModelScope.launch {
            loginInProgress.value = true
            refreshTokenUseCase.attemptRefresh()
            loginInProgress.value = false
        }
    }

    override fun login() = viewModelScope.launch {
        loginInProgress.value = true
        when (loginUseCase.login(username.value, password.value)) {
            is LoginResult.Unauthorized -> dialogEvents.send(DialogEvent(R.string.error_unauthorized))
            is LoginResult.NetworkError -> dialogEvents.send(DialogEvent(R.string.error_network))
            is LoginResult.HttpError -> dialogEvents.send(DialogEvent(R.string.error_http))
            else -> Unit
        }
        loginInProgress.value = false
    }
}
