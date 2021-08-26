package hu.ahomolya.androidbase.startup.impl

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import hu.ahomolya.androidbase.BaseTest
import hu.ahomolya.androidbase.R
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest : BaseTest() {
    @Test
    fun `login page title should be present when starting app`() {
        activityRule.scenario.onActivity {
            onView(withId(R.id.page_title)).check(matches(withEffectiveVisibility(VISIBLE)))
            onView(withId(R.id.page_title)).check(matches(withText(R.string.login_title)))
        }
    }
}