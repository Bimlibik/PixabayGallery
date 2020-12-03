package ru.bimlibik.pixabaygallery.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import ru.bimlibik.pixabaygallery.R

fun View.showSnackbar(message: String) {
    Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .show()
}


fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Int>>
) {
    snackbarEvent.observe(lifecycleOwner, { event ->
        event.getContentIfNotHandled()?.let {
            showSnackbar(context.getString(it))
        }
    })
}