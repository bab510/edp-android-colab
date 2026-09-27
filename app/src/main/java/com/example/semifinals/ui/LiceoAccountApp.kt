package com.example.semifinals.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semifinals.ui.theme.SEMIFINALSTheme

// GIVEN (read it, do not change it)
@Composable
fun LiceoAccountApp(vm: AuthViewModel = viewModel()) {
    var screen by rememberSaveable { mutableStateOf("login") }
    val systemInDark = isSystemInDarkTheme()
    var isDarkTheme by rememberSaveable { mutableStateOf(systemInDark) }
    val state = vm.uiState

    SEMIFINALSTheme(darkTheme = isDarkTheme) {
        if (state is AuthUiState.LoggedIn) {
            ProfileScreen(
                user = state.user,
                onLogout = { vm.logout(); screen = "login" },
                isDarkTheme = isDarkTheme,
                onToggleDarkTheme = { isDarkTheme = !isDarkTheme }
            )
        } else if (screen == "register") {
            RegisterScreen(
                state = state,
                onCreate = vm::register,
                onGoToLogin = { vm.clearMessage(); screen = "login" },
                isDarkTheme = isDarkTheme,
                onToggleDarkTheme = { isDarkTheme = !isDarkTheme }
            )
        } else {
            LoginScreen(
                state = state,
                onLogin = vm::login,
                onGoToRegister = { vm.clearMessage(); screen = "register" },
                isDarkTheme = isDarkTheme,
                onToggleDarkTheme = { isDarkTheme = !isDarkTheme }
            )
        }
    }
}
