package com.azerva.googlecredentialmanager.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.azerva.googlecredentialmanager.data.GoogleAuthImp
import com.azerva.googlecredentialmanager.data.GoogleResult
import com.azerva.googlecredentialmanager.presentation.login.LoginActivity
import javax.inject.Inject

/**
 * This class handles the login process using Google authentication.
 *
 * Think of it like a "login helper" that takes care of all the steps needed to sign you in.
 * It uses Google's tools to make sure everything is safe and secure.
 *
 * @param googleAuthImp The helper that talks to Google for signing in and out.
 * @param credentialManager The tool that manages your login information.
 * @param context The environment where the app is running.
 */
class LoginUseCases @Inject constructor(
    private val googleAuthImp: GoogleAuthImp,
    private val credentialManager: CredentialManager,
    private val context: Context
) {

    /**
     * Gets the Google login information using the Credential Manager.
     *
     * This function asks Google for the login details needed to sign you in.
     *
     * @param activityProvider Provides the current Activity where the login is happening.
     * @return The Google login information.
     */
    suspend fun getCredentialManager(activityProvider: CredentialManagerActivity): GetCredentialResponse =
        credentialManager.getCredential(activityProvider.getActivity(), resultCredentialResponse())

    /**
     * Creates a request for Google login details.
     *
     * This function prepares the "form" we send to Google to ask for login information.
     *
     * @return The request for Google login details.
     */
    private fun resultCredentialResponse(): GetCredentialRequest =
        googleAuthImp.getCredentialRequest()


    /**
     * Handles the Google sign-in process after getting the login information.
     *
     * This function checks if the login was successful and tells us if there were any problems.
     *
     * @param result The response from Google with the login information.
     * @return A [GoogleResult] telling us if sign-in was successful or what went wrong.
     */
    suspend fun handlerSignIn(result: GetCredentialResponse): GoogleResult =
        googleAuthImp.handleGoogleSignIn(result)


}