package com.example.semifinals.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semifinals.domain.model.User

@Composable
fun ProfileScreen(
    user: User,
    onLogout: () -> Unit,
    isDarkTheme: Boolean = false,
    onToggleDarkTheme: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("My Profile", style = MaterialTheme.typography.headlineSmall)
            TextButton(onClick = onToggleDarkTheme) {
                Text(if (isDarkTheme) "☀️ Light" else "🌙 Dark")
            }
        }

        // TODO 12a: a green Card that says "You successfully logged in!"
        // and under it "Welcome back, <fullName>."
        val cardBg = if (isDarkTheme) Color(0xFF1B4D2E) else Color(0xFFE8F5E9)
        val cardTitleColor = if (isDarkTheme) Color(0xFFA6F4C5) else Color(0xFF1B5E20)

        Card(
            colors = CardDefaults.cardColors(containerColor = cardBg),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "You successfully logged in!",
                    fontWeight = FontWeight.Bold,
                    color = cardTitleColor
                )
                Text("Welcome back, ${user.fullName}.")
            }
        }

        // TODO 12c: four ProfileRow(...) calls: Full name, Email, Birthdate, User ID
        ProfileRow("Full name", user.fullName)
        ProfileRow("Email", user.email)
        ProfileRow("Birthdate", user.birthdate)
        ProfileRow("User ID", user.id)

        // BONUS TODO 14d (Part G only): an "Age" row goes here
        ageFrom(user.birthdate)?.let {
            ProfileRow("Age", "$it years old")
        }

        // TODO 12d: Button "Log out" -> onLogout()
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log out")
        }
    }
}

// GIVEN (read it, do not change it): one label with its value under it
@Composable
fun ProfileRow(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

// TODO 14: Returns the age in years, or null if the birthdate cannot be read.
fun ageFrom(birthdate: String): Int? {
    val parts = birthdate.split("-")
    if (parts.size != 3) return null
    val y = parts[0].toIntOrNull() ?: return null
    val m = parts[1].toIntOrNull() ?: return null
    val d = parts[2].toIntOrNull() ?: return null

    val now = java.util.Calendar.getInstance()
    val ty = now.get(java.util.Calendar.YEAR)
    val tm = now.get(java.util.Calendar.MONTH) + 1
    val td = now.get(java.util.Calendar.DAY_OF_MONTH)

    var age = ty - y
    if (tm < m || (tm == m && td < d)) age -= 1
    return age
}
