package com.azerva.googlecredentialmanager.core

import android.content.Context
import android.widget.Toast

/**
 * Displays a toast message in the given [Context] with a long duration.
 *
 * This extension function simplifies showing a toast message by defaulting
 * the duration to [Toast.LENGTH_LONG].
 *
 * @param text The message to be displayed in the toast.
 */
fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}