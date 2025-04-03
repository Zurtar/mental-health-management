package com.zurtar.mhma.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zurtar.mhma.auth.AuthState
import com.zurtar.mhma.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Represents the UI state for the navigation drawer.
 *
 * This data class holds the state regarding the user's login status, which is
 * used for controlling the visibility and accessibility of various features
 * in the navigation drawer, such as the login or logout options.
 *
 * @property isLoggedIn A Boolean value indicating whether the user is logged in.
 * Defaults to `false`
 */
data class NavDrawerUiState(
    val isLoggedIn: Boolean = false
)

/**
 * ViewModel responsible for managing the navigation drawer UI state, specifically
 * tracking the user's login state, and providing it to the UI layer.
 *
 * The [NavigationViewModel] uses the [UserRepository] to observe the user's authentication state
 * and updates the UI state accordingly. It listens for changes in the authentication state
 * and reflects these changes in the [NavDrawerUiState].
 *
 * This ViewModel is scoped to the lifecycle of the navigation UI and ensures that the UI
 * is updated with the correct login state based on the user's authentication status.
 *
 * @param userRepository The repository that handles user authentication and retrieval of
 * authentication state. Injected via Hilt for dependency management.
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    //mutable state to hold the current UI state of the navigation drawer.
    private val _uiState = MutableStateFlow(NavDrawerUiState())

    //exposed state that the UI can collect to get updates on the navigation drawer state.
    val uiState: StateFlow<NavDrawerUiState> = _uiState.asStateFlow()

    init {
        //collects the authentication state from the repository and updates the UI state.
        viewModelScope.launch {
            userRepository.getAuthState().collect { authState ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoggedIn = authState != AuthState.Unauthenticated
                    )
                }
            }
        }
    }
}