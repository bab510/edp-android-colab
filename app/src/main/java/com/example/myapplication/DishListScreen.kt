package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
fun DishListScreen(
    viewModel: DishViewModel,
    onDishClick: (Int) -> Unit
) {
    val themeMaroon = Color(0xFF800000)
    val themeOffWhite = Color(0xFFFAF9F6)

    val dishes by viewModel.dishes.collectAsStateWithLifecycle()
    var newDishName by remember { mutableStateOf("") }
    var dishBeingEdited by remember { mutableStateOf<Dish?>(null) }

    Scaffold(
        containerColor = themeOffWhite,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MyRecipeBook", fontWeight = FontWeight.Bold) },
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
            // TODO 7 (10 pts) -- CREATE
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newDishName,
                    onValueChange = { newDishName = it },
                    label = { Text("New dish name") },
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
                        viewModel.addDish(newDishName)
                        newDishName = ""
                    },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = themeMaroon, contentColor = themeOffWhite)
                ) { Text("Add") }
            }

            Spacer(Modifier.height(16.dp))

            // TODO 8 (15 pts) -- READ + UPDATE + DELETE
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = dishes, key = { it.id }) { dish ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDishClick(dish.id) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    dish.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = themeMaroon
                                )
                                Text(
                                    "${dish.recipes.size} steps",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                            IconButton(onClick = { dishBeingEdited = dish }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = themeMaroon)
                            }
                            IconButton(onClick = { viewModel.deleteDish(dish.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }

        val editing = dishBeingEdited
        if (editing != null) {
            EditDialog(
                title = "Edit dish name",
                initialText = editing.name,
                onConfirm = { newName ->
                    viewModel.updateDish(editing.id, newName)
                    dishBeingEdited = null
                },
                onDismiss = { dishBeingEdited = null }
            )
        }
    }
}
