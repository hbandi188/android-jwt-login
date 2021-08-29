package hu.ahomolya.androidbase.networking.service.impl

import hu.ahomolya.androidbase.networking.BaseUnitTest
import hu.ahomolya.androidbase.networking.api.LoginApi
import hu.ahomolya.androidbase.networking.contract.SecretsProvider
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.networking.model.internal.TokenResponse
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

internal class LoginServiceImplTest : BaseUnitTest() {
    @MockK
    lateinit var loginApi: LoginApi

    @MockK
    lateinit var secretsProvider: SecretsProvider

    @InjectMockKs
    lateinit var tested: LoginServiceImpl

    @Before
    fun setup() {
        every { secretsProvider.getClientId() } returns "clientId"
        coEvery { loginApi.login(any(), any(), any(), any()) } returns TokenResponse("", "", 0, "")
    }

    @Test
    fun `should call API with username and password`() = runBlockingTest {

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

    @Test
    fun `should return the tokens it receives from the Api`() = runBlockingTest {
        every { secretsProvider.getClientId() } returns "clientId"

        val tokenResponse = TokenResponse("accessToken", "tokenType", 42, "refreshToken")
        coEvery { loginApi.login(any(), any(), any(), any()) } returns tokenResponse

        val result = tested.login("username", "password")

        result.shouldBeInstanceOf<LoginResult.Success>()
        result.tokens shouldBe tokenResponse
    }
}