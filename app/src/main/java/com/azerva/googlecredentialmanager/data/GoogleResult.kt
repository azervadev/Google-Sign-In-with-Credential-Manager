package com.azerva.googlecredentialmanager.data

/**
 * Represents the possible outcomes of a Google-related operation (like signing in).
 *
 * Think of it like a set of labels for different results you might get back.
 * Instead of just saying "it worked" or "it didn't work", this class gives you more specific reasons.
 */
sealed class GoogleResult {

    /**
     * Indicates that the Google operation was successful! Everything went smoothly.
     */
    data object Success : GoogleResult()

    /**
     * Indicates that a generic error occurred during the Google operation.
     *
     * This is a simple error state when a specific error type isn't needed or available.
     */
    data object Error : GoogleResult()


    /**
     * Indicates that there was an error related to the Google API (Application Programming Interface).
     * This could be a problem with the connection to Google's servers, or something else on their end.
     */
    data object ApiException : GoogleResult()

    /**
     * Indicates that there was an error trying to get the user's credentials.
     * This might happen if your app couldn't access the information it needed to sign the user in.
     */
    data object GetCredentialException : GoogleResult()

    /**
     * Indicates that some other kind of unexpected error occurred.
     * If you see this, it means something went wrong that wasn't covered by the other specific errors.
     */
    data object Exception : GoogleResult()

}