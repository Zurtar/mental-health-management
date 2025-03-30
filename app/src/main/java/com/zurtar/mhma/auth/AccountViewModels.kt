package com.zurtar.mhma.auth


// im unsure about the project structure, ive moved this into a dedicated model folder.
// I dont  have time to focus on the best way to arrange the file structure though.

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.zurtar.mhma.data.MoodRemoteDataSource
import com.zurtar.mhma.data.MoodRepository
import com.zurtar.mhma.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This file will hold no UI logic!! This is a ViewModel/Buisness Logic Class only!
 * (a little ui logic for the viewmodel)
 *
 *
 * ~~~gonna make this garden groww~~
 * ~~~inch by inch, row, by row~~~~~
 */


data class LoginUiState(
    val email: String = "",
    val password: String = ""
)

data class SignupUiState(
    val email: String = "",
    val password: String = "",
    val password_: String = ""
)

data class AccountUiState(
    val isLoggedIn: Boolean = false,
    val email: String = "default_initial",
    val displayName: String = "default_initial"
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    val userState = Firebase

    fun onEmailChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = newValue
            )
        }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                password = newValue
            )
        }
    }

    fun login(onResult: () -> Unit) {
//      I need to do some checks for empty passwords, and valid emails!

        accountService.authenticate(
            uiState.value.email,
            uiState.value.password,
            onResult = { error ->
                // OnResult
                if (error == null)
                    onResult()
                //Handle error!
            })
    }
}

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountService: AccountService
) : ViewModel() {
//    private val accountService: AccountServiceImplementation = AccountServiceImplementation()

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = newValue
            )
        }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(password = newValue)
        }
    }

    fun onVerifyPasswordChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(password_ = newValue)
        }
    }

    fun createAccount(onResult: () -> Unit) {
        if (_uiState.value.email == "" || _uiState.value.password == "")
            return


        accountService.createAccount(
            email = _uiState.value.email,
            password = _uiState.value.password,
            onResult = { error ->
                // OnResult
                if (error == null)
                    onResult()
                //Handle error!
            })
    }
}

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountService: AccountService
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    init {
//        viewModelScope.launch{
//            userRepository.getAuthState()
//        }


        Firebase.auth.addAuthStateListener { auth ->
            Log.println(
                Log.INFO,
                "AccountViewModel_FirebaseListener",
                "Auth State Listener Fired currentUser:${auth.currentUser}"
            )

            /**
             * This event will fire on Authentication change, sign in sign out, if we update
             * regardless we'll trigger a lot of redundant recompositions. So we check if there is a mistmatch
             * between the userState and our isLoggedIn value. This is ugly, and im sure theres a better way
             * but you are welcome to find it :D
             */

            if (auth.currentUser == null && _uiState.value.isLoggedIn) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = false,
                        email = "",
                        displayName = ""
                    )
                }
            }

            if (auth.currentUser != null && _uiState.value.isLoggedIn == false)
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true,
                        email = auth.currentUser?.email ?: "N/A",
                        displayName = auth.currentUser?.displayName ?: "N/A"
                    )
                }
        }
    }


    fun signOut(onResult: () -> Unit) {
        accountService.logout { error ->
            // OnResult
            if (error == null)
                onResult()
            //Handle error!
        }
    }
}


//https://stackoverflow.com/collectives/google-cloud/articles/68104924/listen-for-authentication-state-in-android