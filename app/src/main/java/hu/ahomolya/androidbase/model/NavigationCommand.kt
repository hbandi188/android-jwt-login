package hu.ahomolya.androidbase.model

import androidx.annotation.IdRes
import androidx.navigation.NavController

/**
 * Navigation commands that can be sent through the navigation channel to be observed by the
 * nav host.
 */
sealed class NavigationCommand {
    abstract fun sendToNavController(navController: NavController)

    /**
     * A navigation that only references an action id.
     */
    data class Resource(@IdRes val navigationId: Int) : NavigationCommand() {
        override fun sendToNavController(navController: NavController) =
            navController.navigate(navigationId)
    }
}
