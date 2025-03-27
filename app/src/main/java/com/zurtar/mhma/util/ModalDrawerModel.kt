package com.zurtar.mhma.util

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class NavDrawerUiState(
    val isLoggedIn: Boolean = false
)

class ModalDrawerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavDrawerUiState())
    val uiState: StateFlow<NavDrawerUiState> = _uiState.asStateFlow()

    init {
        Firebase.auth.addAuthStateListener { auth ->
            Log.println(
                Log.INFO,
                "NavigationDrawerViewModel_FirebaseListener",
                "Auth State Listener Fired currentUser:${auth.currentUser}"
            )

            /**
             * This event will fire on Authentication change, sign in sign out, if we update
             * regardless we'll trigger a lot of redundant recompositions. So we check if there is a mistmatch
             * between the userState and our isLoggedIn value. This is ugly, and im sure theres a better way
             * but you are welcome to find it :D
             */
            if (auth.currentUser == null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = false,
                    )
                }
            }

            if (auth.currentUser != null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true,
                    )
                }
            }
        }
    }

}