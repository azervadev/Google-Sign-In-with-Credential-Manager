package com.azerva.googlecredentialmanager.core

import android.app.Application
import android.content.Context
import androidx.credentials.CredentialManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This module provides dependencies for the entire application.
 *
 * It's installed in the [SingletonComponent], meaning the provided objects will
 * have the same instance throughout the app's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Provides the application's [Context].
     *
     * The application context is a global context that's not tied to any specific
     * activity or fragment. It's useful for accessing resources and performing
     * operations that don't require a UI context.
     *
     * @param application The [Application] instance.
     * @return The application's [Context].
     */
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    /**
     * Provides a singleton instance of [CredentialManager].
     *
     * The [CredentialManager] is used to manage user credentials, like passwords
     * and passkeys. Using `@Singleton` ensures that only one instance is created,
     * which helps with performance and consistency.
     *
     * @param context The application [Context].
     * @return A singleton instance of [CredentialManager].
     */
    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }
}