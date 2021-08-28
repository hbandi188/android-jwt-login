package hu.ahomolya.androidbase.networking.service.impl

import hu.ahomolya.androidbase.networking.BaseUnitTest
import hu.ahomolya.androidbase.networking.api.LoginApi
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

internal class LoginServiceImplTest : BaseUnitTest() {
    @MockK
    lateinit var loginApi: LoginApi

    @MockK
    lateinit var secretsProvider: SecretsProvider

    @InjectMockKs
    lateinit var tested: LoginServiceImpl

    @Test
    fun `should call API with username and password`() = runBlockingTest {
        every { secretsProvider.getClientId() } returns "clientId"

        tested.login("username", "password")

        coVerify {
            loginApi.login(
                username = "username",
                password = "password",
                grantType = "password",
                clientId = "clientId",
            )
        }

        verify { secretsProvider.getClientId() }
    }
}