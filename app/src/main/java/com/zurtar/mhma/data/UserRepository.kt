package com.zurtar.mhma.data

import com.google.android.gms.auth.api.Auth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.zurtar.mhma.auth.AccountService
import com.zurtar.mhma.auth.AuthState
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class that manages user-related operations such as authentication state changes,
 * retrieving user data (like email and display name) from Firestore, and interacting with the FirebaseAuth service.
 *
 * @property accountService The service used for managing account-related operations.
 * @property firestore The Firestore instance used for interacting with the Firestore database.
 * @property firebaseAuth The FirebaseAuth instance used for managing Firebase Authentication.
 */
@Singleton
class UserRepository @Inject constructor(
    private val accountService: AccountService,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    /**
     * Exposes a [Flow] that emits the current authentication state of the user, such as whether the user is authenticated or unauthenticated.
     *
     * @return A [Flow] of [AuthState] representing the user's authentication state.
     */
    fun getAuthState(): Flow<AuthState> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            trySend(if (user != null) AuthState.Authenticated(user) else AuthState.Unauthenticated)
        }

        // Send the initial auth state
        trySend(if (firebaseAuth.currentUser != null) AuthState.Authenticated(firebaseAuth.currentUser!!) else AuthState.Unauthenticated)

        // Add the listener to observe auth state changes
        firebaseAuth.addAuthStateListener(authStateListener)

        // Ensure the listener is removed when the flow is closed
        awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
    }

    /**
     * Retrieves the user's email from Firestore. The email is fetched from the user's document.
     *
     * @return The user's email address as a [String].
     */
    suspend fun getUserEmail(): String {
        return firestore.collection("users").document(firebaseAuth.currentUser?.uid ?: "")
            .get().await().get("email").toString()
    }

    /**
     * Retrieves the user's display name from Firestore. The display name is fetched from the user's document.
     *
     * @return The user's display name as a [String].
     */
    suspend fun getDisplayName(): String {
        return firestore.collection("users").document(firebaseAuth.currentUser?.uid ?: "")
            .get().await().get("display_name").toString()
    }
}
