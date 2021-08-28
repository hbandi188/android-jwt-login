package hu.ahomolya.androidbase.networking

import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before

abstract class BaseUnitTest {
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setupMockK() {
        Dispatchers.setMain(testCoroutineDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
    }
}