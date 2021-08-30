package hu.ahomolya.androidbase.ui.login

import hu.ahomolya.androidbase.contract.DialogEventSource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LoginViewModel : DialogEventSource {
    /**
     * The asynchronous job that was launched when the implementation class was constructed.
     */
    val startupJob: Job

    /**
     * Whether there's work in progress.
     */
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