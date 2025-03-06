package com.azerva.googlecredentialmanager.domain

/**
 * This class represents the different states of the login screen.
 *
 * Think of it like a little notepad that keeps track of what's happening on the login screen.
 * It helps us know if the app is busy loading something or if it's just waiting for you.
 *
 * @property isLoading Indicates whether the app is currently loading something.
 * If it's `true`, it means the app is busy; if it's `false`, it's all set.
 */
data class LoginViewState(
    val isLoading: Boolean = false
)
