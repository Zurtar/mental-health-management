package com.zurtar.mhma.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.util.DefaultTopAppBar


/**
 * Account Screen & Content
 */

/**
 * Composable function for the Account screen, where users can view their account information
 * and log out.
 *
 * @param modifier The modifier to be applied to the layout of the screen content.
 * @param viewModel The ViewModel that holds the UI state and business logic for the account.
 * @param openDrawer A lambda function triggered to open the navigation drawer.
 * @param onLogoutResult A lambda function triggered when the logout process is complete.
 */
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    onLogoutResult: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        AccountScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onLogout = { viewModel.signOut(onLogoutResult) },
            email = uiState.email,
            name = uiState.displayName
        )
    }
}

/**
 * Composable function for the content of the Account screen. Displays the user's email, name,
 * and provides an option to log out.
 *
 * @param modifier The modifier to be applied to the layout of the content.
 * @param onLogout A lambda function triggered when the user chooses to log out.
 * @param email The email address of the logged-in user.
 * @param name The display name of the logged-in user.
 */
@Composable
private fun AccountScreenContent(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    email: String,
    name: String
) {
    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        Text("Account Page")
        Spacer(Modifier.height(25.dp))
        Text(
            "Email: ${email}"
        )
        Text(
            text = "Welcome ${name}!"
        )
        Spacer(Modifier.height(10.dp))
        OutlinedButton(
            onClick = onLogout,
            content = { Text("Logout") }
        )
    }
}


/**
 * Login Screen & Content
 */


/**
 * Composable function for the Login screen that handles the layout and actions
 * for user login and navigation to the sign-up screen.
 *
 * @param modifier The modifier to be applied to the layout of the screen content.
 * @param viewModel The ViewModel that holds the UI state and business logic for login.
 * @param openDrawer A lambda function triggered to open the navigation drawer.
 * @param onLoginResult A lambda function triggered when the login process is complete.
 * @param onNavigateToSignUp A lambda function triggered when the user wants to navigate to the sign-up screen.
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    onLoginResult: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        LoginScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            email = uiState.email,
            password = uiState.password,
            onLogin = { viewModel.login(onResult = onLoginResult) },
            onNavigateToSignUp = onNavigateToSignUp,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange
        )
    }
}


/**
 * Composable function that contains the content for the Login screen.
 *
 * This includes fields for the user's email and password, as well as buttons for login
 * and navigating to the sign-up page.
 *
 * @param modifier The modifier to be applied to the layout of the screen content.
 * @param email The email input by the user.
 * @param password The password input by the user.
 * @param onEmailChange A lambda function called when the email field value changes.
 * @param onPasswordChange A lambda function called when the password field value changes.
 * @param onLogin A lambda function triggered when the "Login" button is clicked.
 * @param onNavigateToSignUp A lambda function triggered when the "Sign Up" button is clicked to navigate to the sign-up screen.
 */
@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(25.dp))
        Text("Login Page")
        Spacer(Modifier.height(25.dp))
        EmailTextField(email, onEmailChange)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(password, onPasswordChange)
        Spacer(Modifier.height(12.5.dp))

        OutlinedButton(
            onClick = onLogin,
        ) {
            Text(text = "Login")
        }
        Text("Or")
        OutlinedButton(
            onClick = onNavigateToSignUp,
        ) {
            Text("Sign Up")
        }
        Spacer(Modifier.height(12.5.dp))
    }
}


/**
 * SignUp Screen & Content
 */

/**
 * Composable function representing the Sign Up screen.
 *
 * This screen allows the user to sign up for an account by providing their email,
 * password, and confirming the password. It calls the provided [onSignUpResult] callback
 * after a successful sign-up.
 *
 * @param modifier The modifier to be applied to the [Scaffold] layout.
 * @param viewModel The [SignupViewModel] instance used to manage UI state and actions.
 * @param openDrawer A lambda function that triggers the opening of a navigation drawer.
 * @param onSignUpResult A lambda function that is invoked when the sign-up process finishes successfully.
 */
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
    openDrawer: () -> Unit,
    onSignUpResult: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            DefaultTopAppBar(openDrawer = openDrawer)
        }
    ) { innerPadding ->
        SignUpScreenContent(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            email = uiState.email,
            password = uiState.password,
            password_ = uiState.password_,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onVerifyPasswordChange = viewModel::onVerifyPasswordChange,
            onSignUp = { viewModel.createAccount(onSignUpResult) }
        )
    }
}

/**
 * Composable function that contains the content for the Sign Up screen.
 *
 * This includes fields for the user's email, password, and confirming the password, as well
 * as the "Sign Up" button.
 *
 * @param modifier The modifier to be applied to the layout of the screen content.
 * @param email The email input by the user.
 * @param password The password input by the user.
 * @param password_ The confirmation password input by the user.
 * @param onEmailChange A lambda function called when the email field value changes.
 * @param onPasswordChange A lambda function called when the password field value changes.
 * @param onVerifyPasswordChange A lambda function called when the confirm password field value changes.
 * @param onSignUp A lambda function triggered when the "Sign Up" button is clicked.
 */
@Composable
private fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    password_: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onVerifyPasswordChange: (String) -> Unit,
    onSignUp: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(25.dp))
        Text("Sign Up Page")
        Spacer(Modifier.height(25.dp))
        EmailTextField(email, onEmailChange)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(password, onPasswordChange)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(password_, onVerifyPasswordChange, "Confirm Password")

        Spacer(Modifier.height(12.5.dp))
        OutlinedButton(
            onClick = onSignUp
        ) {
            Text(
                text = "Sign Up",
            )
        }
        Spacer(Modifier.height(12.5.dp))
    }
}

/**
 * Composable function for the password text field used in the sign-up form.
 *
 * This displays a text field with a password visual transformation and handles the input
 * of a password or confirmation password.
 *
 * @param value The current value of the password field.
 * @param onNewValue A lambda function called when the value of the password field changes.
 * @param label The label to be displayed above the password field (default is "Enter password").
 */
@Composable
private fun PasswordTextField(
    value: String,
    onNewValue: (String) -> Unit,
    label: String = "Enter password"
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text(label) },
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

/**
 * Composable function for the email text field used in the sign-up form.
 *
 * This displays a text field for the user to input their email address.
 *
 * @param value The current value of the email field.
 * @param onNewValue A lambda function called when the value of the email field changes.
 */
@Composable
private fun EmailTextField(value: String, onNewValue: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text("Enter Email") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}