package com.zurtar.mhma

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AccountPage() {
    Column(
        modifier = Modifier
            .fillMaxHeight(.75f),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {

        Text("Account Page")
        Spacer(Modifier.height(25.dp))
        Text(
//            "Email: ${userState.userInfo?.email ?: ""}"
            text = "Email: "
        )
        Text(
//            text = "Welcome, ${userState.userInfo?.email?.substringBefore('@') ?: ""}"
            text = "Welcome, "
        )
        OutlinedButton(
            onClick = { TODO() },
            content = { Text("Logout") }
        )
    }
}

@Composable
fun LoginScreen(modifier: Modifier) {
    val scope = rememberCoroutineScope()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    var (response, setResponse) = remember { mutableStateOf<String>("") }


    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(25.dp))
        Text("Login Page")
        Spacer(Modifier.height(25.dp))
        EmailTextField(email)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(password)
        Spacer(Modifier.height(12.5.dp))

        OutlinedButton(
            onClick = { TODO() },
//            colors = ButtonDefaults.buttonColors(ButtonColor)
        ) {
            Text(
                text = "Login",
//                color = Color.White
            )
        }
        Text("Or")
        OutlinedButton(
            onClick = { TODO() },
//            colors = ButtonDefaults.buttonColors(ButtonColor)
        ) {
            Text(
                "Sign Up",
            )
        }
        Spacer(Modifier.height(12.5.dp))
        Text(
            "Debug Greeting!"
        )
    }
}

@Composable
fun SignUpScreen(modifier: Modifier) {
    val scope = rememberCoroutineScope()

    val email = remember { mutableStateOf("") }

    val password = remember { mutableStateOf("") }
    val password_ = remember { mutableStateOf("") }

    var (response, setResponse) = remember { mutableStateOf<String>("") }

    Column(
        modifier = modifier.fillMaxHeight(.75f),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(25.dp))

        Text("Sign Up Page")

        Spacer(Modifier.height(25.dp))
        EmailTextField(email)

        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(password)
        Spacer(Modifier.height(12.5.dp))
        PasswordTextField(password_, "Confirm Password")

        Spacer(Modifier.height(12.5.dp))
        OutlinedButton(
            onClick = {
                scope.launch { TODO() }
            },
//            colors = ButtonDefaults.buttonColors(ButtonColor)
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
fun PasswordTextField(password: MutableState<String>, label: String = "Enter password") {
//    var password by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text(label) },
        maxLines = 1,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun EmailTextField(email: MutableState<String>) {
//    var email by remember { mutableStateOf("") }
    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("Enter Email") },
        maxLines = 1
    )
}