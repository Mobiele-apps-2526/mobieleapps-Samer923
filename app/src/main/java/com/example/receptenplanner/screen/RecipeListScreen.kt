package com.example.receptenplanner.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.receptenplanner.data.Recipe
import com.example.receptenplanner.viewmodel.RecipeViewModel
import androidx.compose.material.icons.filled.Search

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun RecipeListScreen(viewModel: RecipeViewModel, onNavigateToDetail: (Int) -> Unit) {

    val recipes by viewModel.filteredRecipes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mijn Recepten") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToDetail(-1) }) { // Gebruik de callback
                Icon(Icons.Filled.Add, contentDescription = "Nieuw Recept")
            }
        }
    ) { paddingValues ->
        // Gebruik Column om de zoekbalk boven de LazyColumn te plaatsen
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // De Zoekbalk
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Zoek recepten op titel/ingrediënt...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Zoeken") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            RecipeList(
                recipeList = recipes,
                modifier = Modifier.weight(1f),
                onItemClick = onNavigateToDetail
            )
        }
    }
}




@Composable
fun RecipeListItem(recipe: Recipe, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = recipe.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            // Toon een stukje van de ingrediënten
            Text(text = "Ingrediënten: ${recipe.ingredients.take(50)}...", style = MaterialTheme.typography.bodySmall)
        }
    }
    Divider()
}