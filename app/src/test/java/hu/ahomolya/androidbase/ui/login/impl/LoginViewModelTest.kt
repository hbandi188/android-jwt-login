package hu.ahomolya.androidbase.ui.login.impl

import hu.ahomolya.androidbase.BaseUnitTest
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.networking.model.internal.TokenResponse
import hu.ahomolya.androidbase.usecases.LoginUseCase
import hu.ahomolya.androidbase.usecases.RefreshTokenUseCase
import io.kotest.matchers.collections.shouldEndWith
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class LoginViewModelTest : BaseUnitTest() {

    @MockK
    lateinit var loginUseCase: LoginUseCase

    @MockK
    lateinit var refreshTokenUseCase: RefreshTokenUseCase

    @InjectMockKs
    lateinit var tested: LoginViewModelImpl

    @Before
    fun setup() {
        val tokens = TokenResponse("", "", 0, "")
        coEvery { loginUseCase.login(any(), any()) } returns LoginResult.Success(tokens)
    }

    @Test
    fun `view model default values`() {
        tested.username.value shouldBe ""
        tested.password.value shouldBe ""
        tested.enableLogin.value shouldBe false
    }

    @Test
    fun `filling out fields should enable login button`() {
        tested.username.value = "user"
        tested.password.value = "pass"

        tested.enableLogin.value shouldBe true
    }

    @Test
    fun `if login fields are filled out, can call use case`() {
        tested.username.value = "username"
        tested.password.value = "password"

        tested.login()

        coVerify { loginUseCase.login("username", "password") }
    }

    @Test
    fun `while logging in, a flag should indicate to show the progress indicator`() {
        tested.username.value = "username"
        tested.password.value = "password"
        val history = tested.loginInProgress.shareIn(CoroutineScope(testCoroutineDispatcher), Eagerly, replay = Int.MAX_VALUE)

        tested.login()

        testCoroutineDispatcher.advanceUntilIdle()
        history.replayCache shouldEndWith listOf(false, true, false)
    }

    @Test
    fun `should attempt to refresh access token upon init`() = runBlockingTest {
        // test subject is already constructed at this point

        tested.startupJob.join()

        coVerify { refreshTokenUseCase.attemptRefresh() }
    }

    @Ignore("pauseDispatcher doesn't really work as advertised...")
    @Test
    fun `shows progress indicator while token extension is in progress`() = runBlockingTest {
        pauseDispatcher {
            val tested = LoginViewModelImpl(loginUseCase, refreshTokenUseCase)
            val history = tested.loginInProgress.shareIn(CoroutineScope(testCoroutineDispatcher), Eagerly, replay = Int.MAX_VALUE)

            advanceUntilIdle()
            tested.startupJob.join()

            testCoroutineDispatcher.advanceUntilIdle()
            history.replayCache shouldEndWith listOf(false, true, false)
        }
    }
}