package com.zurtar.mhma.models


// im unsure about the project structure, ive moved this into a dedicated model folder.
// I dont  have time to focus on the best way to arrange the file structure though.

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.zurtar.mhma.auth.AccountServiceImplementation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * This file will hold no UI logic!! This is a ViewModel/Buisness Logic Class only!
 * (a little ui logic for the viewmodel)
 *
 *
 * ~~~gonna make this garden groww~~
 * ~~~inch by inch, row, by row~~~~~
 */


/**
 * TODO:
 *  - Theres some redundancy, we have listeners inside the viewmodels being added to the auth object, those listeners then have
 *    logic to update their Ui State, to trigger recomposition. However that means that we have these data classes holding UI State for
 *    the various pages, which is fine, but for screens where we only want to check *if* someone is logged in we have this isLoggedIn varriable,
 *    then if we want to acesss user data we re-create those same fields that are in the other data classes (email, displayname, blahblah)
 *    .
 *    Is there not a better way to do this? Do we have to implement this listener code in every viewmodel? Surely theres a way to setup the one
 *    listener as a stateflow, fire off some event trigger anand then have other viewmodels inherit/react to *that* trigger instead of the direct.
 *    .
 *    the current approach is like having 7 children staring at a parent and the parent shoots the message to each, the children all specifically handling
 *    how they respond to it, but all defined ahead of time. Where the implementation I'd like to switch to would be the parent rings a bell, sending out
 *    one signal, the children then react, and can also tell other children that arent listening for that bell. I dont like the bulk of logic all being in that
 *    listener, or that im adding listeners in the init function of these view models. When the lifecycle of the viewmodel is up, is that listener removed?
 *    .
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

data class NavDrawerUiState(
    val isLoggedIn: Boolean = false
)

data class BiWeeklyEvaluationUiState(
    val depressionScore: Int = 0,
    val anxietyScore: Int = 0,
    val page: Int = 0,
    val questionResponse: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
)

class LoginViewModel : ViewModel() {
    private val accountService: AccountServiceImplementation = AccountServiceImplementation()

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

class SignupViewModel : ViewModel() {
    private val accountService: AccountServiceImplementation = AccountServiceImplementation()

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

class AccountViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    private val accountService: AccountServiceImplementation = AccountServiceImplementation()

    init {
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

class NavigationDrawerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavDrawerUiState())
    val uiState: StateFlow<NavDrawerUiState> = _uiState.asStateFlow()

    init {
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

            if (auth.currentUser == null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = false,
                    )
                }
            }

            if (auth.currentUser != null)
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = true,
                    )
                }
        }
    }

}

class BiWeeklyEvaluationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BiWeeklyEvaluationUiState())
    val uiState: StateFlow<BiWeeklyEvaluationUiState> = _uiState.asStateFlow()


    /**
     * Triggered when Next button is pressed
     * Should increment page count and possibly do verification
     */
    fun onNext() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page + 1)
        }

        if (_uiState.value.page == 15)
            debugScore()
    }

    fun onBack() {
        _uiState.update { currentState ->
            currentState.copy(page = currentState.page - 1)
        }
    }

    fun onSelect(selected: Int) {
        val newList = _uiState.value.questionResponse.toMutableList()
        newList[_uiState.value.page] = selected

        _uiState.update { currentState ->
            currentState.copy(
                questionResponse = newList
            )
        }
    }

    fun debugScore() {
        val depressionScore = _uiState.value.questionResponse.subList(0, 9).sum()
        val anxietyScore = _uiState.value.questionResponse.subList(9, 15).sum()

        _uiState.update { currentState ->
            currentState.copy(
                depressionScore = depressionScore,
                anxietyScore = anxietyScore
            )
        }

        Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Depression Score: $depressionScore")
        Log.println(Log.DEBUG, "BiWeeklyEvalVM", "Anxiety Score: $anxietyScore")
    }

    fun resetPage() {
        _uiState.update { currentState ->
            currentState.copy(page = 0)
        }
    }
}


//https://stackoverflow.com/collectives/google-cloud/articles/68104924/listen-for-authentication-state-in-android