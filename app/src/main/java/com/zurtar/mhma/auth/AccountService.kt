package com.zurtar.mhma.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.time.Instant

/**
 *
 *
 * Let me ask you a question, you don't look like a dancer but would be make an exception?
 */

interface AccountService {
    fun createAnonymousAccount(onResult: (Throwable?) -> Unit)
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun logout(onResult: (Throwable?) -> Unit)
}

class AccountServiceImplementation : AccountService {
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

                Firebase.firestore.collection("users").document("$uid")
                    .set("online" to Instant.now().toString())
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