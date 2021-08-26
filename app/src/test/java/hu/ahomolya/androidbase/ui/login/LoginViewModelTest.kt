package hu.ahomolya.androidbase.ui.login

import hu.ahomolya.androidbase.BaseUnitTest
import hu.ahomolya.androidbase.ui.login.impl.LoginViewModelImpl
import io.kotest.matchers.shouldBe
import org.junit.Test

class LoginViewModelTest : BaseUnitTest() {
    @Test
    fun `view model default values`() {
        val tested: LoginViewModel = LoginViewModelImpl()

        tested.username.value shouldBe ""
        tested.password.value shouldBe ""
        tested.enableLogin.value shouldBe false
    }

    @Test
    fun `filling out fields should enable login button`() {
        val tested: LoginViewModel = LoginViewModelImpl()

        tested.username.value = "user"
        tested.password.value = "pass"

        tested.enableLogin.value shouldBe true
    }
}