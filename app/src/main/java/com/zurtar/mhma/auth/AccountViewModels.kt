package com.zurtar.mhma.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the UI state for the login screen.
 * @property email The email input by the user.
 * @property password The password input by the user.
 */
data class LoginUiState(
    val email: String = "",
    val password: String = ""
)

/**
 * Represents the UI state for the signup screen.
 * @property email The email input by the user.
 * @property password The password input by the user.
 * @property password_ The verification password input by the user.
 */
data class SignupUiState(
    val email: String = "",
    val password: String = "",
    val password_: String = ""
)

/**
 * Represents the UI state for the user's account.
 * @property isLoggedIn Whether the user is currently authenticated.
 * @property email The email associated with the logged-in account.
 * @property displayName The display name of the authenticated user.
 */
data class AccountUiState(
    val isLoggedIn: Boolean = false,
    val email: String = "default_initial",
    val displayName: String = "default_initial"
)

/**
 * ViewModel responsible for managing the login process.
 * Handles user authentication and UI state updates.
 *
 * @property userRepository Repository handling user-related data operations.
 * @property accountService Service responsible for authentication operations.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Updates the email field in the UI state.
     * @param newValue The new email value.
     */
    fun onEmailChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(email = newValue)
        }
    }

    /**
     * Updates the password field in the UI state.
     * @param newValue The new password value.
     */
    fun onPasswordChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(password = newValue)
        }
    }

    /**
     * Attempts to log the user in using the provided credentials.
     * @param onResult Callback function executed on success.
     */
    fun login(onResult: () -> Unit) {
        try {
            accountService.authenticate(
                uiState.value.email,
                uiState.value.password,
                onResult = { error ->
                    if (error == null) {
                        onResult()
                    } else {
                        _uiState.update { it.copy(email = "INVALID_LOGIN", password = "") }
                    }
                }
            )
        } catch (e: Exception) {
            _uiState.update { it.copy(email = "INVALID_LOGIN", password = "") }
        }
    }
}

/**
 * ViewModel responsible for managing the signup process.
 * Handles account creation and UI state updates.
 *
 * @property userRepository Repository handling user-related data operations.
 * @property accountService Service responsible for authentication operations.
 */
@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    /**
     * Updates the email field in the UI state.
     * @param newValue The new email value.
     */
    fun onEmailChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(email = newValue)
        }
    }

    /**
     * Updates the password field in the UI state.
     * @param newValue The new password value.
     */
    fun onPasswordChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(password = newValue)
        }
    }

    /**
     * Updates the verification password field in the UI state.
     * @param newValue The new verification password value.
     */
    fun onVerifyPasswordChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(password_ = newValue)
        }
    }

    /**
     * Attempts to create a new user account.
     * @param onResult Callback function executed on success.
     */
    fun createAccount(onResult: () -> Unit) {
        if (_uiState.value.email.isBlank() || _uiState.value.password.isBlank()) return

        accountService.createAccount(
            email = _uiState.value.email,
            password = _uiState.value.password,
            onResult = { error ->
                if (error == null) {
                    onResult()
                }
                // Handle error (not currently implemented)
            }
        )
    }
}

/**
 * ViewModel responsible for managing user account state and authentication status.
 * It listens for authentication state changes and provides relevant user data.
 *
 * @property userRepository Repository handling user-related data operations.
 * @property accountService Service responsible for authentication operations.
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getAuthState().collect { authState ->
                _uiState.update { currentState ->
                    if (authState != AuthState.Unauthenticated) {
                        currentState.copy(
                            isLoggedIn = true,
                            displayName = userRepository.getDisplayName(),
                            email = userRepository.getUserEmail()
                        )
                    } else {
                        currentState.copy(
                            isLoggedIn = false,
                            displayName = "LOGGED_OUT",
                            email = "LOGGED_OUT"
                        )
                    }
                }
            }
        }
    }

    /**
     * Signs the user out and updates the UI state accordingly.
     * @param onResult Callback function executed on success.
     */
    fun signOut(onResult: () -> Unit) {
        accountService.logout { error ->
            if (error == null) {
                onResult()
            }
            // Handle error (not currently implemented)
        }
    }
}