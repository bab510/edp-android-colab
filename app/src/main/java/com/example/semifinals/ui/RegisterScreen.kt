package com.example.semifinals.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    state: AuthUiState,
    onCreate: (String, String, String, String) -> Unit,
    onGoToLogin: () -> Unit,
    isDarkTheme: Boolean = false,
    onToggleDarkTheme: () -> Unit = {}
) {
    // TODO 11a: four rememberSaveable text states: fullName, email, password, birthdate
    var fullName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var birthdate by rememberSaveable { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val isLoading = state is AuthUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Create account", style = MaterialTheme.typography.headlineMedium)
            TextButton(onClick = onToggleDarkTheme) {
                Text(if (isDarkTheme) "☀️ Light" else "🌙 Dark")
            }
        }

        // TODO 11b: OutlinedTextField "Full name"
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // TODO 11c: OutlinedTextField "Email" (email keyboard)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        // TODO 11d: OutlinedTextField "Password" (hidden)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { showPassword = !showPassword }) {
                    Text(if (showPassword) "Hide" else "Show")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // TODO 11e: OutlinedTextField "Birthdate" with placeholder "YYYY-MM-DD"
        OutlinedTextField(
            value = birthdate,
            onValueChange = { birthdate = it },
            label = { Text("Birthdate") },
            placeholder = { Text("YYYY-MM-DD") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // TODO 11f: Error -> red message. AccountCreated -> green message
        if (state is AuthUiState.Error) {
            Text(state.message, color = MaterialTheme.colorScheme.error)
        }
        if (state is AuthUiState.AccountCreated) {
            val greenColor = if (isDarkTheme) Color(0xFF81C784) else Color(0xFF2E7D32)
            Text(
                "Account created for ${state.name}. You can now log in.",
                color = greenColor
            )
        }

        // TODO 11g: Button "Create account" -> onCreate(fullName, email, password, birthdate),
        // disabled while loading (spinner like TODO 10d)
        Button(
            onClick = { onCreate(fullName, email, password, birthdate) },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Create account")
            }
        }

        // TODO 11h: TextButton "Already have an account? Log in" -> onGoToLogin()
        TextButton(onClick = onGoToLogin) {
            Text("Already have an account? Log in")
        }
    }
}
