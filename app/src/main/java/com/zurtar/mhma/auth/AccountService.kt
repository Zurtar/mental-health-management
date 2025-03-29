package com.zurtar.mhma.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import java.time.Instant
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

class AccountServiceImplementation @Inject constructor(
) : AccountService {
    override fun createAnonymousAccount(onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception)
                val uid = it.result.user?.uid ?: return@addOnCompleteListener
                val user = it.result.user!!
                user.email!!

                val updates = hashMapOf<String, Any>(
                    "email" to user.email!!,
                    "last_login" to FieldValue.serverTimestamp()
                )

                Firebase.firestore.collection("users").document(uid)
                    .set(updates)
            }
    }

    override fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)

        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun logout(onResult: (Throwable?) -> Unit) {
        Firebase.auth.signOut()

        // this cant detect errors, but signOut doesnt have an onComplete, because no external network call is needed.
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
