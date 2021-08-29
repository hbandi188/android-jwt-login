package hu.ahomolya.androidbase.ui.login.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.ahomolya.androidbase.ui.login.LoginViewModel
import hu.ahomolya.androidbase.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), LoginViewModel {
    override val username: MutableStateFlow<String> = MutableStateFlow("")
    override val password: MutableStateFlow<String> = MutableStateFlow("")
    override val enableLogin: StateFlow<Boolean> = combine(username, password) { un, pw -> un.isNotBlank() && pw.isNotBlank() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
    override val loginInProgress = MutableStateFlow(false)

    override fun login() = viewModelScope.launch {
        loginInProgress.value = true
        loginUseCase.login(username.value, password.value)
        loginInProgress.value = false
    }
}
