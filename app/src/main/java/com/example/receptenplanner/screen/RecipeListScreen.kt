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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(navController: NavController, viewModel: RecipeViewModel) {
    val recipeListUiState by viewModel.recipeListUiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mijn Recepten") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Destinations.ADD_RECIPE_ROUTE) }) {
                Icon(Icons.Filled.Add, contentDescription = "Recept toevoegen")
            }
        }
    ) { paddingValues ->
        RecipeList(
            recipeList = recipeListUiState.recipeList,
            modifier = Modifier.padding(paddingValues),
            onItemClick = { recipeId ->
                navController.navigate("${Destinations.RECIPE_DETAIL_ROUTE}/$recipeId")
            }
        )
    }
}

@Composable
fun RecipeList(
    recipeList: List<Recipe>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = recipeList, key = { it.id }) { recipe ->

            RecipeListItem(recipe = recipe, onClick = { onItemClick(recipe.id) })
        }
    }
    if (recipeList.isEmpty()) {
        Text(text = "Geen recepten gevonden. Voeg er een toe!", modifier = Modifier.padding(16.dp))
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