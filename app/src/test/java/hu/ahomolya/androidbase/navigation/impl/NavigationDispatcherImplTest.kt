package hu.ahomolya.androidbase.navigation.impl

import hu.ahomolya.androidbase.BaseUnitTest
import hu.ahomolya.androidbase.R
import hu.ahomolya.androidbase.model.NavigationCommand
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class NavigationDispatcherImplTest : BaseUnitTest() {
    @MockK
    lateinit var navigationChannel: Channel<NavigationCommand>

    @InjectMockKs
    lateinit var tested: NavigationDispatcherImpl

    @Test
    fun `should dispatch navigation events to the channel`() = runBlockingTest {
        tested.dispatch(R.id.goToHomeScreen)

        coVerify { navigationChannel.send(NavigationCommand.Resource(R.id.goToHomeScreen)) }
    }
}