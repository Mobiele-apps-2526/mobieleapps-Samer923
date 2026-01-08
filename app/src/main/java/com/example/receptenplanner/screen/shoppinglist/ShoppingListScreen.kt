package com.example.receptenplanner.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.receptenplanner.data.ShoppingListItem
import com.example.receptenplanner.viewmodel.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(viewModel: RecipeViewModel) {
    val items by viewModel.shoppingListItems.collectAsState(initial = emptyList())
    var newItemName by remember { mutableStateOf("") }
    val showInput = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Boodschappenlijst") },
                actions = {
                    IconButton(
                        onClick = { viewModel.clearShoppingList() },
                        enabled = items.isNotEmpty()
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Lijst legen")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showInput.value = !showInput.value }) {
                Icon(Icons.Filled.Add, contentDescription = "Item toevoegen")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            AnimatedVisibility(
                visible = showInput.value,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newItemName,
                        onValueChange = { newItemName = it },
                        label = { Text("Nieuw Item") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            if (newItemName.isNotBlank()) {
                                viewModel.insertShoppingListItem(newItemName)
                                newItemName = ""
                                showInput.value = false
                            }
                        },
                        enabled = newItemName.isNotBlank()
                    ) {
                        Text("Voeg toe")
                    }
                }
            }

            if (items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("De boodschappenlijst is leeg.")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        ShoppingListItemRow(
                            item = item,
                            onToggleCheck = {
                                viewModel.updateShoppingListItem(item.copy(isChecked = !item.isChecked))
                            },
                            onDelete = {
                                viewModel.deleteShoppingListItem(item)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListItemRow(
    item: ShoppingListItem,
    onToggleCheck: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggleCheck),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if (item.isChecked) TextDecoration.LineThrough else null,
                    color = if (item.isChecked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = item.isChecked,
                    onCheckedChange = { onToggleCheck() }
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Verwijder item",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}