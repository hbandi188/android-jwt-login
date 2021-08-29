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
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import okio.IOException
import org.junit.Test
import java.time.Clock
import java.time.Instant

class RefreshTokenUseCaseImplTest : BaseUnitTest() {
    @MockK
    lateinit var preferencesStore: PreferencesStore

    @MockK
    lateinit var loginService: LoginService

    @MockK
    lateinit var navigationDispatcher: NavigationDispatcher

    @MockK
    lateinit var clock: Clock

    @InjectMockKs
    lateinit var tested: RefreshTokenUseCaseImpl

    @Test
    fun `does nothing if no tokens are present`() = runBlockingTest {
        every { preferencesStore.tokens } returns null

        tested.attemptRefresh()

        verify { preferencesStore.tokens }
        confirmVerified(preferencesStore, loginService, navigationDispatcher)
    }

    @Test
    fun `gets tokens, attempts refresh and stores new tokens if successful`() = runBlockingTest {
        val now = Instant.ofEpochMilli(1_000_000)
        every { preferencesStore.tokens } returns SavedTokens("", "refreshToken", Instant.ofEpochMilli(500_000))
        val tokens = TokenResponse("newAccessToken", "tokenType", 10, "refreshToken")
        coEvery { loginService.refresh(any()) } returns LoginResult.Success(tokens)
        every { clock.instant() } returns now

        tested.attemptRefresh()

        verify { preferencesStore.tokens }
        val newTokens = slot<SavedTokens>()
        verify { preferencesStore.tokens = capture(newTokens) }
        coVerify { loginService.refresh("refreshToken") }
        coVerify { navigationDispatcher.dispatch(R.id.goToHomeScreen) }
        confirmVerified(preferencesStore, loginService, navigationDispatcher)

        newTokens.captured.accessToken shouldBe "newAccessToken"
        newTokens.captured.refreshToken shouldBe "refreshToken"
        newTokens.captured.validUntil shouldBe now.plusSeconds(10)
    }

    @Test
    fun `does not update preferences store or navigate if refresh fails`() = runBlockingTest {
        every { preferencesStore.tokens } returns SavedTokens("", "refreshToken", Instant.ofEpochMilli(500_000))
        coEvery { loginService.refresh(any()) } returns LoginResult.NetworkError(IOException())

        tested.attemptRefresh()

        verify { preferencesStore.tokens }
        coVerify { loginService.refresh("refreshToken") }
        confirmVerified(preferencesStore, loginService, navigationDispatcher)
    }
}