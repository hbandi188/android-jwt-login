package hu.ahomolya.androidbase.usecases.impl

import hu.ahomolya.androidbase.BaseUnitTest
import hu.ahomolya.androidbase.R
import hu.ahomolya.androidbase.model.SavedTokens
import hu.ahomolya.androidbase.navigation.NavigationDispatcher
import hu.ahomolya.androidbase.networking.model.LoginResult
import hu.ahomolya.androidbase.networking.model.internal.TokenResponse
import hu.ahomolya.androidbase.networking.service.LoginService
import hu.ahomolya.androidbase.store.PreferencesStore
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.Instant

class LoginUseCaseImplTest : BaseUnitTest() {
    @MockK
    lateinit var loginService: LoginService

    @MockK
    lateinit var preferencesStore: PreferencesStore

    @MockK
    lateinit var clock: Clock

    @MockK
    lateinit var navigationDispatcher: NavigationDispatcher

    @InjectMockKs
    lateinit var tested: LoginUseCaseImpl

    @Before
    fun setup() {
        every { clock.instant() } returns Instant.now()
    }

    @Test
    fun `should invoke service when logging in`() = runBlockingTest {
        coEvery { loginService.login(any(), any()) } returns LoginResult.Unauthorized

        tested.login("username", "password")

        coVerify { loginService.login("username", "password") }
    }

    @Test
    fun `should return login result to viewmodel`() = runBlockingTest {
        val success = LoginResult.Success(TokenResponse("", "", 0, ""))
        coEvery { loginService.login(any(), any()) } returns success

        val result = tested.login("username", "password")

        result shouldBe success
    }

    @Test
    fun `should store tokens in preferences storage if successful`() = runBlockingTest {
        val success = LoginResult.Success(TokenResponse("accessToken", "tokenType", 0, "refreshToken"))
        coEvery { loginService.login(any(), any()) } returns success
        every { clock.instant() } returns Instant.ofEpochMilli(1630267327000)

        tested.login("username", "password")

        val slot = slot<SavedTokens>()
        verify { preferencesStore.tokens = capture(slot) }
        slot.isCaptured shouldBe true
        slot.captured.accessToken shouldBe "accessToken"
        slot.captured.refreshToken shouldBe "refreshToken"
        slot.captured.validUntil shouldBe Instant.ofEpochMilli(1630267327000)
    }

    @Test
    fun `should navigate to next screen on success`() = runBlockingTest {
        val success = LoginResult.Success(TokenResponse("", "", 0, ""))
        coEvery { loginService.login(any(), any()) } returns success

        tested.login("username", "password")

        coVerify { navigationDispatcher.dispatch(R.id.goToHomeScreen) }
    }
}