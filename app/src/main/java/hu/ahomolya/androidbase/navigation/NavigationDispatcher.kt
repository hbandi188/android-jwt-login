package hu.ahomolya.androidbase.navigation

import androidx.annotation.IdRes

/**
 * Dispatches navigation commands to the singleton navigation channel.
 */
interface NavigationDispatcher {
    /**
     * Perform the specified navigation based on a navigation id.
     */
    suspend fun dispatch(@IdRes navigationId: Int)
}
