package com.example.receptenplanner.ui.theme.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.receptenplanner.models.model.Recipe
import com.example.receptenplanner.viewmodel.RecipeViewModel

import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeViewModel,
    onNavigateToDetail: (Int) -> Unit
) {

    val recipes by viewModel.filteredRecipes.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mijn Recepten") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToDetail(-1) }) {
                Icon(Icons.Filled.Add, contentDescription = "Nieuw Recept")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Zoek Recepten") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Zoeken") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true
            )

            if (recipes.isEmpty() && searchQuery.isBlank()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Geen recepten gevonden.")
                }
            } else if (recipes.isEmpty() && searchQuery.isNotBlank()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Geen resultaten voor '${searchQuery}'")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(recipes) { recipe ->
                        RecipeListItem(recipe = recipe, onClick = { onNavigateToDetail(recipe.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeListItem(recipe: Recipe, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${recipe.ingredients.size} ingrediënten",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}