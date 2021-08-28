package hu.ahomolya.androidbase.ui.login.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.ahomolya.androidbase.ui.login.LoginViewModel
import hu.ahomolya.androidbase.usecases.LoginUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), LoginViewModel {
    override val username: MutableStateFlow<String> = MutableStateFlow("")
    override val password: MutableStateFlow<String> = MutableStateFlow("")
    override val enableLogin: StateFlow<Boolean> = combine(username, password) { un, pw -> un.isNotBlank() && pw.isNotBlank() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
    override val loginInProgress = MutableStateFlow(false)

    override fun login() {
        loginInProgress.value = true
        loginUseCase.login(username.value, password.value)
        loginInProgress.value = false
    }
}
