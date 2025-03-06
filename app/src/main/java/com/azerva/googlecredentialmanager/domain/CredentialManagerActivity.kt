package com.azerva.googlecredentialmanager.domain

import android.app.Activity

/**
 * This interface helps us get the current Activity when we need it.
 *
 * Imagine you have a helper that needs to know which room you're in.
 * This interface is like a way for the helper to ask, "Which room are we in right now?"
 * The Activity is like the "room" in our app.
 */
interface CredentialManagerActivity {
    /**
     * Gets the current Activity.
     *
     * This function is like the helper asking, "Where are we?"
     * It gives back the Activity, which is like the "room" where the app is currently showing things.
     *
     * @return The current Activity.
     */
    fun getActivity(): Activity
}