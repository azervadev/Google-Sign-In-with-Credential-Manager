package com.azerva.googlecredentialmanager.data

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse

/**
 * This interface defines the actions that can be performed for Google authentication.
 *
 * Think of it like a list of instructions for a helper that can talk to Google.
 * It tells us what the helper can do to sign you in or out of your Google account.
 */
interface GoogleAuthDataSource {

    /**
     * Tries to sign the user in to their Google account.
     *
     * This function will attempt to start the Google sign-in process.
     * It might take a little while, like when you're waiting for a webpage to load.
     *
     * @param result The response obtained after attempting to get Google credentials.
     * It's like the result of asking Google to let you in.
     * @return A [GoogleResult] indicating whether the sign-in was successful, or what kind of error occurred.
     * It's like getting a message back telling you if everything went okay or if something went wrong.
     */
    suspend fun handleGoogleSignIn(result: GetCredentialResponse): GoogleResult

    /**
     * Creates a request to get Google credentials.
     *
     * This function prepares a "request" to ask Google for the data needed to sign in.
     * It's like writing a letter asking for permission to enter.
     *
     * @return A [GetCredentialRequest] containing the information needed for sign-in.
     */
    fun getCredentialRequest(): GetCredentialRequest

    /**
     * Signs the user out of their Google account.
     *
     * This function ends the user's current Google session.
     * It might also take a short time to complete.
     */
    suspend fun googleSignOut()
}