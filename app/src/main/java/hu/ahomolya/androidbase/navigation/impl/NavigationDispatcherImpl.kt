package hu.ahomolya.androidbase.navigation.impl

import hu.ahomolya.androidbase.model.NavigationCommand
import hu.ahomolya.androidbase.navigation.NavigationDispatcher
import kotlinx.coroutines.channels.Channel

class NavigationDispatcherImpl(
    private val navigationChannel: Channel<NavigationCommand>
) : NavigationDispatcher {
    override suspend fun dispatch(navigationId: Int) {
        navigationChannel.send(NavigationCommand.Resource(navigationId))
    }
}