package hu.ahomolya.androidbase

import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Rule

@Suppress("LeakingThis")
open class BaseRobolectricTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)

    @get:Rule
    val hiltRule = HiltAndroidRule(this)
}