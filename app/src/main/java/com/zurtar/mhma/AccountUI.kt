package com.zurtar.mhma

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zurtar.mhma.models.AccountViewModel
import com.zurtar.mhma.models.LoginViewModel
import com.zurtar.mhma.models.SignupViewModel


//Split up into ViewModel likely
@Composable
fun AccountScreen(
    modifier: Modifier,
    viewModel: AccountViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        Alignment.CenterHorizontally
    ) {


        Text("Account Page")
        Spacer(Modifier.height(25.dp))
        Text(
            "Email: ${uiState.email}"
        )
        Text(
            text = "Welcome, ${uiState.displayName}"
        )
        OutlinedButton(
            onClick = { viewModel.signOut(onLogout) },
            content = { Text("Logout") }
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(25.dp))
        Text("Login Page")
        Spacer(Modifier.height(25.dp))
        EmailTextField(uiState.email, viewModel::onEmailChange)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(uiState.password, viewModel::onPasswordChange)
        Spacer(Modifier.height(12.5.dp))

        OutlinedButton(
            onClick = { viewModel.login(onResult = onLoginSuccess) },
//            colors = ButtonDefaults.buttonColors(ButtonColor)
        ) {
            Text(text = "Login")
        }
        Text("Or")
        OutlinedButton(
            onClick = { },
//            colors = ButtonDefaults.buttonColors(ButtonColor)
        ) {
            Text("Sign Up")
        }
        Spacer(Modifier.height(12.5.dp))
        Text(
            "Debug: Ah!"
        )
    }
}

@Composable
fun SignUpScreen(
    viewModel: SignupViewModel = viewModel(),
    modifier: Modifier,
    onSignUp: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(25.dp))

        Text("Sign Up Page")

        Spacer(Modifier.height(25.dp))
        EmailTextField(uiState.email, viewModel::onEmailChange)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(uiState.password, viewModel::onPasswordChange)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(uiState.password_, viewModel::onVerifyPasswordChange, "Confirm Password")

        Spacer(Modifier.height(12.5.dp))
        OutlinedButton(
            onClick = {
                viewModel.createAccount(onSignUp)
            },
        ) {
            Text(
                text = "Sign Up",
//                color = Color.White
            )
        }
        Spacer(Modifier.height(12.5.dp))
        Text("Debug Greeting!")
    }
}

@Composable
fun PasswordTextField(
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
fun EmailTextField(value: String, onNewValue: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text("Enter Email") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}