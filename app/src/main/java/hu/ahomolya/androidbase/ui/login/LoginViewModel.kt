package hu.ahomolya.androidbase.ui.login

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LoginViewModel {
    val loginInProgress: StateFlow<Boolean>

    /**
     * The current value of the username field.
     */
    val username: MutableStateFlow<String>

    /**
     * The current value of the password field.
     */
    val password: MutableStateFlow<String>

    /**
     * Whether to enable the login button.
     */
    val enableLogin: StateFlow<Boolean>

    /**
     * Start login process. Will check if input is valid before proceeding.
     */
    fun login(): Job
}