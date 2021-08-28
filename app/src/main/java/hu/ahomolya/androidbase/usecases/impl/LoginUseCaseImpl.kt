package hu.ahomolya.androidbase.usecases.impl

import hu.ahomolya.androidbase.networking.service.LoginService
import hu.ahomolya.androidbase.usecases.LoginUseCase

class LoginUseCaseImpl(private val loginService: LoginService) : LoginUseCase {
    override fun login(username: String, password: String) {
        loginService.login(username, password)
    }
}
