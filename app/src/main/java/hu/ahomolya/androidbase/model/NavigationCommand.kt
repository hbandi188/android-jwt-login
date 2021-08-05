package hu.ahomolya.androidbase.model

import androidx.annotation.IdRes

/**
 * Navigation commands that can be sent through the navigation channel to be observed by the
 * nav host.
 */
sealed class NavigationCommand {
    /**
     * A navigation that only references an action id.
     */
    data class Resource(@IdRes val navigationId: Int) : NavigationCommand()
}
