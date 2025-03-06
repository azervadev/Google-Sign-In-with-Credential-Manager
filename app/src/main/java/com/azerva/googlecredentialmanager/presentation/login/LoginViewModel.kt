package com.azerva.googlecredentialmanager.presentation.login

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azerva.googlecredentialmanager.data.GoogleResult
import com.azerva.googlecredentialmanager.domain.CredentialManagerActivity
import com.azerva.googlecredentialmanager.domain.LoginUseCases
import com.azerva.googlecredentialmanager.domain.LoginViewState
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * This class manages the login screen and handles the logic for signing in with Google.
 *
 * Think of it like the "brain" of the login screen. It takes care of all the thinking and decision-making.
 * It uses the login helper (LoginUseCases) to do the actual work of signing you in.
 *
 * @param useCases The helper that takes care of the login process.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: LoginUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginViewState())
    val uiState: StateFlow<LoginViewState> = _uiState

    private val _googleResult = MutableStateFlow<GoogleResult?>(null)
    val googleResult: StateFlow<GoogleResult?> = _googleResult

    /**
     * Tries to sign the user in with their Google account.
     *
     * This function starts the process of signing you in using your Google account.
     * It will ask you to choose a Google account and then try to log you in.
     *
     * @param activityProvider Provides the current screen (Activity) where the login is happening.
     */
    fun signInWithGoogle(activityProvider: CredentialManagerActivity) = viewModelScope.launch {
        _uiState.value = LoginViewState(isLoading = true)
        try {
            val response = useCases.getCredentialManager(activityProvider)
            val result = useCases.handlerSignIn(response)
            _googleResult.value = result

        } catch (e: GetCredentialException) {
            _googleResult.value = GoogleResult.GetCredentialException // User cancelled
        } finally {
            _uiState.value = LoginViewState(isLoading = false)
        }
    }
}