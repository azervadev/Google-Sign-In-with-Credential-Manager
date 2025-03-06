package com.azerva.googlecredentialmanager.presentation.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.azerva.googlecredentialmanager.R
import com.azerva.googlecredentialmanager.core.launchActivity
import com.azerva.googlecredentialmanager.core.toast
import com.azerva.googlecredentialmanager.data.GoogleResult
import com.azerva.googlecredentialmanager.databinding.ActivityLoginBinding
import com.azerva.googlecredentialmanager.domain.CredentialManagerActivity
import com.azerva.googlecredentialmanager.domain.LoginViewState
import com.azerva.googlecredentialmanager.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * This is the screen where users can log in using their Google accounts.
 *
 * Think of this as the "front door" of the app. It's where you start when you want to get in.
 * It lets you log in with Google and shows you messages about what's happening.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), CredentialManagerActivity {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Makes the app use the full screen
        binding = ActivityLoginBinding.inflate(layoutInflater) // Sets up the layout
        setContentView(binding.root) // Shows the layout on the screen

        // Makes sure the app looks good with different screen sizes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setup() // Calls the setup function
    }

    private fun setup() {
        observers() // Sets up the observers
        listeners() // Sets up the listeners
    }

    private fun listeners() = with(binding) {
        googleSignInButton.setOnClickListener {
            viewModel.signInWithGoogle(this@LoginActivity) // Starts the Google sign-in process
        }
    }

    private fun observers() {
        lifecycleScope.launch {
            // Listens for changes in the login state (loading, etc.)
            viewModel.uiState.collect { state ->
                updateUi(state) // Updates the UI based on the state
            }
        }

        lifecycleScope.launch {
            // Listens for results from the Google sign-in process
            viewModel.googleResult.collect { result ->
                when (result) {
                    is GoogleResult.Success -> {
                        launchActivity<HomeActivity> { } // Goes to the home screen
                    }
                    is GoogleResult.Error -> {
                        toast("Something went wrong with Google credentials") // Shows a message
                    }
                    is GoogleResult.ApiException -> {
                        toast("We encountered an issue with Google's servers.") // Shows a message
                    }
                    is GoogleResult.GetCredentialException -> {
                        toast("There was an issue retrieving your account information.") // Shows a message
                    }
                    is GoogleResult.Exception -> {
                        toast("We encountered an unexpected issue.") // Shows a message
                    }

                    else -> {} // Does nothing for other cases
                }
            }
        }
    }

    private fun updateUi(state: LoginViewState) = with(binding) {
        progressBar.isVisible = state.isLoading // Shows or hides the loading bar
    }

    /**
     * Returns the current Activity.
     *
     * This is like saying, "I'm right here!" It tells other parts of the app where we are.
     *
     * @return The current Activity.
     */
    override fun getActivity(): Activity {
        return this@LoginActivity // Returns the current Activity
    }
}