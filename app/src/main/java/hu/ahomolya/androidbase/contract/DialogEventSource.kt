package hu.ahomolya.androidbase.contract

import androidx.annotation.StringRes
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * A view model can implement this class if it wants to send dialog events to a dialog renderer.
 */
interface DialogEventSource {
    val dialogEvents: ReceiveChannel<DialogEvent>

    /**
     * A descriptor for a single dialog event.
     */
    data class DialogEvent(@StringRes val message: Int)
}