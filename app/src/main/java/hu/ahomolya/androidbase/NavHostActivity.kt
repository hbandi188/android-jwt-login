package hu.ahomolya.androidbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.ahomolya.androidbase.di.DiQualifiers.NavigationChannel
import hu.ahomolya.androidbase.model.NavigationCommand
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    @NavigationChannel
    @Inject
    lateinit var navigationChannel: Channel<NavigationCommand>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listenToNavigationCommands()
    }

    private fun listenToNavigationCommands() {
        lifecycleScope.launchWhenStarted {
            for (command in navigationChannel) {
                when (command) {
                    is NavigationCommand.Resource -> findNavController(R.id.nav_host_fragment).navigate(
                        command.navigationId,
                    )
                }
            }
        }
    }
}