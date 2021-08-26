package hu.ahomolya.androidbase.ui.login

import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import hu.ahomolya.androidbase.BaseRobolectricTest
import hu.ahomolya.androidbase.R
import hu.ahomolya.androidbase.ui.login.impl.LoginViewModelImpl
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(LoginModule::class)
class LoginFragmentTest : BaseRobolectricTest() {
    @BindValue
    @JvmField
    val loginViewModel = mockk<LoginViewModelImpl> {
        every { username } returns MutableStateFlow("")
        every { password } returns MutableStateFlow("")
        every { enableLogin } returns MutableStateFlow(false)
        justRun { login() }
    }

    @Before
    fun setup() {
        clearAllMocks(answers = false)
    }

    @Test
    fun `login page title should be present when starting app`() {
        activityRule.scenario.onActivity {
            onView(withId(R.id.page_title)).check(matches(withEffectiveVisibility(VISIBLE)))
            onView(withId(R.id.page_title)).check(matches(withText(R.string.login_title)))
        }
    }

    @Test
    fun `login page has username field`() {
        activityRule.scenario.onActivity {
            onView(withId(R.id.username_field)).check(matches(withEffectiveVisibility(VISIBLE)))
            onView(withId(R.id.username_field)).check(matches(withHint(R.string.username)))

            val usernameField = it.findViewById<EditText>(R.id.username_field)
            val autofillHints = usernameField.autofillHints
            autofillHints.shouldNotBeNull()
            autofillHints shouldContain "username"
            usernameField.inputType shouldBe TYPE_CLASS_TEXT
        }
    }

    @Test
    fun `login page has password field`() {
        activityRule.scenario.onActivity {
            onView(withId(R.id.password_field)).check(matches(withEffectiveVisibility(VISIBLE)))
            onView(withId(R.id.password_field)).check(matches(withHint(R.string.password)))

            val usernameField = it.findViewById<EditText>(R.id.password_field)
            val autofillHints = usernameField.autofillHints
            autofillHints.shouldNotBeNull()
            autofillHints shouldContain "password"
            usernameField.inputType shouldBe (TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD)
        }
    }

    @Test
    fun `login page should have login button`() {
        activityRule.scenario.onActivity {
            onView(withId(R.id.login_button)).check(matches(withEffectiveVisibility(VISIBLE)))
            onView(withId(R.id.login_button)).check(matches(withText(R.string.login_button)))
        }
    }

    @Test
    fun `binds to viewmodel`() {
        // Based on this discussion:
        // https://github.com/google/dagger/issues/1972
        // Replacing ViewModels in the dependency graph is quite tricky. Also, danysantiago provides a good argument that Fragments &
        // ViewModels together form a unit and should be tested thus. Given how much boilerplate it takes to make this testable, I'd argue
        // it's not worth it, but here it goes anyway:

        activityRule.scenario.onActivity {
            loginViewModel.username.value = "user"
            onView(withId(R.id.username_field)).check(matches(withText("user")))
            loginViewModel.username.value = "dummy"
            onView(withId(R.id.username_field)).check(matches(withText("dummy")))

            onView(withId(R.id.username_field)).perform(replaceText("username"))
            loginViewModel.username.value shouldBe "username"

            loginViewModel.password.value = "pass"
            onView(withId(R.id.password_field)).check(matches(withText("pass")))
            loginViewModel.password.value = "dummy"
            onView(withId(R.id.password_field)).check(matches(withText("dummy")))

            onView(withId(R.id.password_field)).perform(replaceText("password"))
            loginViewModel.password.value shouldBe "password"
        }
    }

    @Test
    fun `enables login button based on viewmodel`() {
        activityRule.scenario.onActivity {
            val loginStateFlow = loginViewModel.enableLogin as MutableStateFlow

            loginStateFlow.value = false
            onView(withId(R.id.login_button)).check(matches(not(isEnabled())))

            loginStateFlow.value = true
            onView(withId(R.id.login_button)).check(matches(isEnabled()))
        }
    }

    @Test
    fun `tapping login button invokes viewmodel`() {
        activityRule.scenario.onActivity {
            val loginStateFlow = loginViewModel.enableLogin as MutableStateFlow

            loginStateFlow.value = true
            onView(withId(R.id.login_button)).perform(click())

            verify { loginViewModel.login() }
        }
    }
}