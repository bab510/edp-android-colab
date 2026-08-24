package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailScreen(
    dishId: Int,
    viewModel: DishViewModel,
    onBack: () -> Unit
) {
    val themeMaroon = Color(0xFF800000)
    val themeOffWhite = Color(0xFFFAF9F6)

    // GIVEN: find the dish this screen is about.
    val dishes by viewModel.dishes.collectAsStateWithLifecycle()
    val dish = dishes.find { it.id == dishId }

    if (dish == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Dish not found", style = MaterialTheme.typography.headlineSmall)
                Button(onClick = onBack) { Text("Go Back") }
            }
        }
        return
    }

    // GIVEN: local UI state.
    var newStep by remember { mutableStateOf("") }
    var stepBeingEdited by remember { mutableStateOf<Recipe?>(null) }

    Scaffold(
        containerColor = themeOffWhite,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(dish.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = themeOffWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = themeMaroon,
                    titleContentColor = themeOffWhite
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                "Recipe steps",
                style = MaterialTheme.typography.titleLarge,
                color = themeMaroon,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))

            // TODO 9 (6 pts) -- CREATE
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newStep,
                    onValueChange = { newStep = it },
                    label = { Text("New step") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = themeMaroon,
                        focusedLabelColor = themeMaroon,
                        cursorColor = themeMaroon
                    )
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        viewModel.addRecipe(dishId, newStep)
                        newStep = ""
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = themeMaroon, contentColor = themeOffWhite)
                ) { Text("Add") }
            }

            Spacer(Modifier.height(16.dp))

            // TODO 10 (10 pts) -- READ + DELETE
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(items = dish.recipes, key = { _, r -> r.id }) { index, recipe ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "${index + 1}.",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = themeMaroon
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                recipe.text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f),
                                color = Color.DarkGray
                            )
                            IconButton(onClick = { stepBeingEdited = recipe }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = themeMaroon)
                            }
                            IconButton(onClick = {
                                viewModel.deleteRecipe(dishId, recipe.id)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }

        // TODO 11 (4 pts) -- UPDATE
        val editing = stepBeingEdited
        if (editing != null) {
            EditDialog(
                title = "Edit step",
                initialText = editing.text,
                onConfirm = { newText: String ->
                    viewModel.updateRecipe(dishId, editing.id, newText)
                    stepBeingEdited = null
                },
                onDismiss = { stepBeingEdited = null }
            )
        }
    }
}

@Composable
fun EditDialog(
    title: String,
    initialText: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
