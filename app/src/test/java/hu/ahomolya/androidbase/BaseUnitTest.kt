package hu.ahomolya.androidbase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before

abstract class BaseUnitTest {
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setupCoroutineDispatcher() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

}