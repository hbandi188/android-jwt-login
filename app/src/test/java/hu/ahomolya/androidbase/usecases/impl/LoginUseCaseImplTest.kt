package hu.ahomolya.androidbase.usecases.impl

import hu.ahomolya.androidbase.BaseUnitTest
import hu.ahomolya.androidbase.networking.service.LoginService
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Test

class LoginUseCaseImplTest : BaseUnitTest() {
    @MockK
    lateinit var loginService: LoginService

    @InjectMockKs
    lateinit var tested: LoginUseCaseImpl

    @Test
    fun `should invoke service when logging in`() {
        tested.login("username", "password")

        verify { loginService.login("username", "password") }
    }
}