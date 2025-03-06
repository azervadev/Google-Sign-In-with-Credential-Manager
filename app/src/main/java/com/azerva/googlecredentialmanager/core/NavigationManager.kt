package com.azerva.googlecredentialmanager.core

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Launches an activity of the specified type [T] with optional extras.
 *
 * This function uses generics and reified types to simplify the creation and launching of an [Activity].
 * It allows passing additional data to the launched activity via the [extras] lambda.
 *
 * @param T The type of the activity to be launched. Must extend [Activity].
 * @param extras An optional lambda to configure the [Intent], allowing you to add extras or set flags. Default is `null`.
 *
 * Example usage:
 * ```
 * // Launch an activity without extras
 * context.launchActivity<MyActivity>()
 *
 * // Launch an activity with extras
 * context.launchActivity<MyActivity> {
 *     putExtra("key", "value")
 *     putExtra("anotherKey", 123)
 * }
 * ```
 */
inline fun <reified T : Activity> Context.launchActivity(
    noinline extras: (Intent.() -> Unit)? = null
) {
    val intent = Intent(this, T::class.java)
    extras?.let { intent.it() }
    startActivity(intent)
}