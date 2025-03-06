package com.azerva.googlecredentialmanager.data

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.azerva.googlecredentialmanager.R
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
/**
 * This class handles Google authentication (signing in and out) using the Credential Manager.
 *
 * It's like the "Google Helper" we talked about earlier, but this is the actual code that does the work!
 * It uses the Credential Manager to make things easier and more secure.
 *
 * @param context The application's context, like the environment where the app is running.
 * @param credentialManager The Credential Manager, which helps manage user credentials.
 */
class GoogleAuthImp @Inject constructor(
    private val context: Context,
    private val credentialManager: CredentialManager
) : GoogleAuthDataSource {

    private val getSignInWithGoogleOption = GetSignInWithGoogleOption
        .Builder(serverClientId = "IMPORTANT: Replace with your actual Web Client ID!")
        .setNonce(generateNonce())
        .build()

    /**
     * Creates a request to get Google sign-in credentials.
     *
     * This prepares the "form" we send to Google to ask for permission to sign in.
     *
     * @return A [GetCredentialRequest] that tells Google what we need.
     */
    override fun getCredentialRequest(): GetCredentialRequest =
        GetCredentialRequest.Builder()
            .addCredentialOption(getSignInWithGoogleOption)
            .build()

    /**
     * Handles the Google sign-in process after getting credentials.
     *
     * This checks if the sign-in was successful and tells us if there were any problems.
     *
     * @param result The response from Google with the sign-in credentials.
     * @return A [GoogleResult] telling us if sign-in was successful or what went wrong.
     */
    override suspend fun handleGoogleSignIn(result: GetCredentialResponse): GoogleResult {
        return try {
            val credential = result.credential
            if (credential is GoogleIdTokenCredential) {
                GoogleResult.Success
            } else {
                GoogleResult.Error
            }
        } catch (e: Exception) {
            GoogleResult.Exception
        } catch (e: ApiException) {
            GoogleResult.ApiException
        }
    }

    /**
     * Signs the user out of their Google account.
     *
     * This ends the user's Google session, like closing the door when you leave.
     */
    override suspend fun googleSignOut() {
        val clearRequest = ClearCredentialStateRequest()
        credentialManager.clearCredentialState(clearRequest)
    }

    /**
     * Generates a secure, one-time number (nonce) for the sign-in process.
     *
     * This is like a secret code that helps keep the sign-in safe.
     *
     * @return A secure nonce string.
     */
    private fun generateNonce(): String {
        val nonce = UUID.randomUUID().toString()
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(nonce.toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(hash, Base64.NO_WRAP or Base64.NO_PADDING)
    }
}