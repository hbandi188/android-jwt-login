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
import okhttp3.ResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

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
        val tokenResponse = TokenResponse("accessToken", "tokenType", 42, "refreshToken")
        coEvery { loginApi.login(any(), any(), any(), any()) } returns tokenResponse

        val result = tested.login("username", "password")

        result.shouldBeInstanceOf<LoginResult.Success>()
        result.tokens shouldBe tokenResponse
    }

    @Test
    fun `should return error result for network errors`() = runBlockingTest {
        coEvery { loginApi.login(any(), any(), any(), any()) } throws IOException("Broken network.")

        val result = tested.login("username", "password")

        result.shouldBeInstanceOf<LoginResult.NetworkError>()
        result.cause.shouldBeInstanceOf<IOException>()
    }

    @Test
    fun `should return specific result for HTTP exceptions`() = runBlockingTest {
        coEvery { loginApi.login(any(), any(), any(), any()) } throws HttpException(
            Response.error<Unit>(HTTP_INTERNAL_ERROR, ResponseBody.create(null, "")),
        )

        val result = tested.login("username", "password")

        result.shouldBeInstanceOf<LoginResult.HttpError>()
        result.code shouldBe HTTP_INTERNAL_ERROR
    }


    @Test
    fun `should return specific result for unauthorized scenarios`() = runBlockingTest {
        coEvery { loginApi.login(any(), any(), any(), any()) } throws HttpException(
            Response.error<Unit>(HTTP_UNAUTHORIZED, ResponseBody.create(null, "")),
        )

        val result = tested.login("username", "password")

        result.shouldBeInstanceOf<LoginResult.Unauthorized>()
    }

    @Test
    fun `refresh - should call API refresh token`() = runBlockingTest {
        tested.refresh("refreshToken")

        coVerify {
            loginApi.refresh(
                refreshToken = "refreshToken",
                grantType = "refresh_token",
                clientId = "clientId",
            )
        }

        verify { secretsProvider.getClientId() }
    }

    @Test
    fun `refresh - should return the tokens it receives from the Api`() = runBlockingTest {
        val tokenResponse = TokenResponse("accessToken", "tokenType", 42, "refreshToken")
        coEvery { loginApi.refresh(any(), any(), any()) } returns tokenResponse

        val result = tested.refresh("refreshToken")

        result.shouldBeInstanceOf<LoginResult.Success>()
        result.tokens shouldBe tokenResponse
    }

    @Test
    fun `refresh - should return error result for network errors`() = runBlockingTest {
        coEvery { loginApi.refresh(any(), any(), any()) } throws IOException("Broken network.")

        val result = tested.refresh("refreshToken")

        result.shouldBeInstanceOf<LoginResult.NetworkError>()
        result.cause.shouldBeInstanceOf<IOException>()
    }

    @Test
    fun `refresh - should return specific result for HTTP exceptions`() = runBlockingTest {
        coEvery { loginApi.refresh(any(), any(), any()) } throws HttpException(
            Response.error<Unit>(HTTP_INTERNAL_ERROR, ResponseBody.create(null, "")),
        )

        val result = tested.refresh("refreshToken")

        result.shouldBeInstanceOf<LoginResult.HttpError>()
        result.code shouldBe HTTP_INTERNAL_ERROR
    }


    @Test
    fun `refresh - should return specific result for unauthorized scenarios`() = runBlockingTest {
        coEvery { loginApi.refresh(any(), any(), any()) } throws HttpException(
            Response.error<Unit>(HTTP_UNAUTHORIZED, ResponseBody.create(null, "")),
        )

        val result = tested.refresh("refreshToken")

        result.shouldBeInstanceOf<LoginResult.Unauthorized>()
    }
}