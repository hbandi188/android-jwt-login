package hu.ahomolya.androidbase.ui.common

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.lifecycleScope
import hu.ahomolya.androidbase.contract.DialogEventSource
import hu.ahomolya.androidbase.contract.DialogEventSource.DialogEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


/**
 * A view that can take [DialogEvent] objects from a [DialogEventSource] and render them on the host fragment or activity.
 */
class DialogRenderer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var source: DialogEventSource? by Delegates.observable(null) { _, old, new ->
        detachFromSource(old)
        attachToSource(new)
    }

    private var collectionJob: Job? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attachToSource(source)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        detachFromSource(source)
    }

    private fun detachFromSource(source: DialogEventSource?) {
        if (source != null && isAttachedToWindow) {
            collectionJob?.cancel()
            collectionJob = null
        }
    }

    private fun attachToSource(source: DialogEventSource?) {
        collectionJob?.cancel()
        if (source != null && isAttachedToWindow) {
            val activity = getActivity()
            collectionJob = activity?.lifecycleScope?.launch {
                source.dialogEvents.consumeAsFlow().collect { event ->
                    activity.showDialog(event)
                }
            }
        }
    }

    private fun AppCompatActivity.showDialog(event: DialogEvent) {
        AlertDialog.Builder(this)
            .setMessage(event.message)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .show()
    }

    // source: https://stackoverflow.com/questions/8276634/how-to-get-hosting-activity-from-a-view
    private fun getActivity(): AppCompatActivity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }
}

@BindingAdapter("eventSource")
fun DialogRenderer.setEventSource(source: DialogEventSource?) {
    this.source = source
}