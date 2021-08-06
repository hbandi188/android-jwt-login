package hu.ahomolya.androidbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.ahomolya.androidbase.di.DiQualifiers.NavigationChannel
import hu.ahomolya.androidbase.model.NavigationCommand
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
        lifecycleScope.launch {
            navigationChannel
                .receiveAsFlow()
                .collect { action ->
                    action.sendToNavController(findNavController(R.id.nav_host_fragment))
                }
        }
    }
}