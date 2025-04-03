package com.zurtar.mhma.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.LocalCacheSettings
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.persistentCacheSettings
import com.zurtar.mhma.chatbot.UserMessageItemReconstruct
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interface defining the various authentication and account management services.
 */
interface AccountService {

    /**
     * Create an anonymous account for the user.
     * This signs the user in anonymously without requiring email or password.
     *
     * @param onResult The callback function to handle the result of the account creation.
     *                It receives an optional [Throwable] indicating the error, if any.
     */
    fun createAnonymousAccount(onResult: (Throwable?) -> Unit)

    /**
     * Create a new account using the provided email and password.
     * This registers a new user in Firebase with the given credentials.
     *
     * @param email The email address of the user.
     * @param password The password to associate with the account.
     * @param onResult The callback function to handle the result of account creation.
     *                It receives an optional [Throwable] indicating the error, if any.
     */
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)

    /**
     * Authenticate a user with their email and password.
     * This logs the user in by validating their credentials against Firebase.
     *
     * @param email The email address of the user.
     * @param password The password associated with the user's email.
     * @param onResult The callback function to handle the result of the authentication.
     *                It receives an optional [Throwable] indicating the error, if any.
     */
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)

    /**
     * Link the current account to a new email and password.
     * This operation links an email and password to the current user account.
     *
     * @param email The email address to link.
     * @param password The password to associate with the email.
     * @param onResult The callback function to handle the result of the linking.
     *                It receives an optional [Throwable] indicating the error, if any.
     */
    fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)

    /**
     * Log out the currently authenticated user.
     * This signs the user out of Firebase and invalidates their session.
     *
     * @param onResult The callback function to handle the result of the logout.
     *                It receives an optional [Throwable] indicating the error, if any.
     */
    fun logout(onResult: (Throwable?) -> Unit)
}

/**
 * Implementation of [AccountService] that interacts with Firebase for user authentication and account management.
 */
@Singleton
class AccountServiceImplementation @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AccountService {

    /**
     * Create an anonymous account for the user.
     * This signs the user in anonymously.
     *
     * @param onResult The callback function to handle the result of the account creation.
     */
    override fun createAnonymousAccount(onResult: (Throwable?) -> Unit) {
        firebaseAuth.signInAnonymously()
            .addOnCompleteListener { onResult(it.exception) }
    }

    /**
     * Create a new account using the provided email and password.
     * This registers a new user with the specified email and password.
     *
     * @param email The email address of the user.
     * @param password The password to associate with the account.
     * @param onResult The callback function to handle the result of account creation.
     */
    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    /**
     * Authenticate a user with their email and password.
     * This logs the user into their account.
     *
     * @param email The email address of the user.
     * @param password The password to associate with the account.
     * @param onResult The callback function to handle the result of authentication.
     */
    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception)
                val uid = it.result.user?.uid ?: return@addOnCompleteListener
                val user = it.result.user!!
                user.email!!

                val updates = hashMapOf<String, Any>(
                    "email" to user.email!!,
                    "display_name" to user.email!!.substringBefore('@'),
                    "last_login" to FieldValue.serverTimestamp()
                )

                firestore.collection("users").document(uid)
                    .set(updates)
            }
    }

    /**
     * Link the current account to a new email and password.
     * This allows the user to associate an email and password with their account.
     *
     * @param email The email address to link.
     * @param password The password to associate with the email.
     * @param onResult The callback function to handle the result of linking the account.
     */
    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)

        firebaseAuth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }

    /**
     * Log out the currently authenticated user.
     * This signs the user out and clears their session.
     *
     * @param onResult The callback function to handle the result of logout.
     */
    override fun logout(onResult: (Throwable?) -> Unit) {
        firebaseAuth.signOut()
        onResult(null)
    }
}

/**
 * Dagger Hilt module to bind the [AccountServiceImplementation] to [AccountService].
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AccountServiceModule {

    /**
     * Binds the [AccountServiceImplementation] to [AccountService].
     *
     * @param accountServiceImplementation The concrete implementation of [AccountService].
     * @return The [AccountService] interface.
     */
    @Binds
    @Singleton
    abstract fun bindAccountService(
        accountServiceImplementation: AccountServiceImplementation
    ): AccountService
}

/**
 * Dagger Hilt module to provide Firebase-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object FireBaseModule {

    /**
     * Provides the [FirebaseAuth] instance for authentication.
     *
     * @return The [FirebaseAuth] instance.
     */
    @Provides
    fun provideFirebaseAuthService(
    ): FirebaseAuth {
        return Firebase.auth
    }


    /**
     * Provides the [FirebaseFirestore] instance for database operations.
     *
     * @return The [FirebaseFirestore] instance with custom settings.
     */
    @Provides
    fun providesFireStoreService(
        // Potential dependencies of this type
    ): FirebaseFirestore {
        val db = Firebase.firestore
        val settings = firestoreSettings {
            setLocalCacheSettings(persistentCacheSettings {})
        }

        return db
    }
}

/**
 * Represents the authentication state of the user
 */
sealed class AuthState {

    /**
     * Represents the state when the user is unauthenticated.
     */
    object Unauthenticated : AuthState()

    /**
     * Represents the state when the user is authenticated, containing the user details.
     *
     * @param user The authenticated [FirebaseUser].
     */
    data class Authenticated(val user: FirebaseUser) : AuthState()
}