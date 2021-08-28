package hu.ahomolya.androidbase.usecases.impl

import hu.ahomolya.androidbase.BaseUnitTest
import hu.ahomolya.androidbase.networking.service.LoginService
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class LoginUseCaseImplTest : BaseUnitTest() {
    @MockK
    lateinit var loginService: LoginService

    @InjectMockKs
    lateinit var tested: LoginUseCaseImpl

    @Test
    fun `should invoke service when logging in`() = runBlockingTest {
        tested.login("username", "password")

        coVerify { loginService.login("username", "password") }
    }
}