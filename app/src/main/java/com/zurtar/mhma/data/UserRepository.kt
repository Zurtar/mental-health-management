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


@Singleton
class UserRepository @Inject constructor(
    private val accountService: AccountService,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    // Expose a flow that sends Authenticated/Unauthenticated updates on Auth change
    fun getAuthState(): Flow<AuthState> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            trySend(if (user != null) AuthState.Authenticated(user) else AuthState.Unauthenticated)
        }

        trySend(if (firebaseAuth.currentUser != null) AuthState.Authenticated(firebaseAuth.currentUser!!) else AuthState.Unauthenticated)

        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
    }

    suspend fun getUserEmail(): String {
        return firestore.collection("users").document(firebaseAuth.currentUser?.uid ?: "").get().await().get("email").toString()
    }

    suspend fun getDisplayName(): String {
        return firestore.collection("users").document(firebaseAuth.currentUser?.uid ?: "").get().await().get("display_name").toString()
    }
}


