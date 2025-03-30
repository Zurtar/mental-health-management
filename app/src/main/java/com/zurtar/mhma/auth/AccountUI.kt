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
            email = uiState.email
        )
    }
}

@Composable
private fun AccountScreenContent(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    email: String
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
            text = "Welcome!"
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