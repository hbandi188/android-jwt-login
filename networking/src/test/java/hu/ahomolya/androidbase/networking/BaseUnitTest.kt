package hu.ahomolya.androidbase.networking

import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before

open class BaseUnitTest {
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setupMockK() {
        Dispatchers.setMain(testCoroutineDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    protected fun loadFile(filename: String): String =
        this::class.java.classLoader?.getResource(filename)?.readText() ?: error("Failed to load $filename.")
}