package hu.ahomolya.androidbase.ui.login

import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import hu.ahomolya.androidbase.BaseRobolectricTest
import hu.ahomolya.androidbase.R
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest : BaseRobolectricTest() {
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
}