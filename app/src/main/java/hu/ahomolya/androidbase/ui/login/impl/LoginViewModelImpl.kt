package hu.ahomolya.androidbase.ui.login.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.ahomolya.androidbase.ui.login.LoginViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor() : ViewModel(), LoginViewModel {
    override val username: MutableStateFlow<String> = MutableStateFlow("")
    override val password: MutableStateFlow<String> = MutableStateFlow("")
    override val enableLogin: StateFlow<Boolean> = combine(username, password) { un, pw -> un.isNotBlank() && pw.isNotBlank() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun login() {
        // TODO
    }
}
