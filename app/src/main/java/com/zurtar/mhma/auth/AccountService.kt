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
 * Let me ask you a question, you don't look like a dancer but would be make an exception?
 */


interface AccountService {
    fun createAnonymousAccount(onResult: (Throwable?) -> Unit)
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun logout(onResult: (Throwable?) -> Unit)
}

/*
interface AccountService {
    fun createAnonymousAccount()
    suspend fun createAccount(email: String, password: String): Result<Unit>
    suspend fun authenticate(email: String, password: String): Result<FirebaseUser>
    suspend fun linkAccount(email: String, password: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
}*/

@Singleton
class AccountServiceImplementation @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AccountService {

    override fun createAnonymousAccount(onResult: (Throwable?) -> Unit) {
        firebaseAuth.signInAnonymously()
            .addOnCompleteListener { onResult(it.exception) }

    }

    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

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

    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)

        firebaseAuth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun logout(onResult: (Throwable?) -> Unit) {
        firebaseAuth.signOut()
        onResult(null)
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class AccountServiceModule {
    @Binds
    @Singleton
    abstract fun bindAccountService(
        accountServiceImplementation: AccountServiceImplementation
    ): AccountService
}


@Module
@InstallIn(SingletonComponent::class)
object FireBaseModule {
    @Provides
    fun provideFirebaseAuthService(
        // Potential dependencies of this type
    ): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    fun providesFireStoreService(
        // Potential dependencies of this type
    ): FirebaseFirestore {
        val db = Firebase.firestore
        val settings = firestoreSettings {
            setLocalCacheSettings(persistentCacheSettings {})
        }

//        db.firestoreSettings = settings
        return db
    }
}


sealed class AuthState {
    object Unauthenticated : AuthState()
    data class Authenticated(val user: FirebaseUser) : AuthState()
}

/*
@Module
@InstallIn(ActivityComponent::class)
object AccountServiceModule {
    @Provides
    fun provideAccountService(
        // Potential dependencies of this type
    ): AccountService {
        return AccountServiceImplementation()
    }
}*/
