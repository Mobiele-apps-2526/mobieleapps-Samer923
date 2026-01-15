package com.example.receptenplanner.ui.theme.recipes



import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.receptenplanner.models.model.Recipe
import com.example.receptenplanner.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    viewModel: RecipeViewModel,
    onBack: () -> Unit
) {
    val recipeFlow = remember(recipeId) {
        if (recipeId != -1) viewModel.getRecipe(recipeId) else null
    }

    val recipe by recipeFlow?.collectAsState(initial = null) ?: remember { mutableStateOf(null) }

    var title by remember { mutableStateOf("") }
    var ingredientsText by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(recipeId != -1) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(recipe) {
        if (recipe != null) {
            title = recipe!!.title
            ingredientsText = recipe!!.ingredients.joinToString(separator = "\n")
            instructions = recipe!!.instructions
            isLoading = false
        } else if (recipeId != -1) {
            isLoading = false
        } else {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(if (recipeId == -1) "Nieuw Recept" else "Recept Bewerken") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Terug")
                    }
                },
                actions = {
                    if (recipeId != -1 && recipe != null) {
                        IconButton(onClick = {
                            viewModel.addRecipeIngredientsToShoppingList(recipe!!.ingredients)
                            scope.launch {
                                snackbarHostState.showSnackbar("Ingrediënten toegevoegd aan de boodschappenlijst!")
                            }
                        }) {
                            Icon(Icons.Filled.AddShoppingCart, contentDescription = "Toevoegen aan lijst")
                        }

                        IconButton(onClick = {
                            viewModel.deleteRecipe(recipe!!)
                            onBack()
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Verwijderen")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titel") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = ingredientsText,
                onValueChange = { ingredientsText = it },
                label = { Text("Ingrediënten (één per regel)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instructies") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Button(
                onClick = {
                    if (title.isNotBlank() && ingredientsText.isNotBlank() && instructions.isNotBlank()) {
                        if (recipeId == -1) {
                            val newRecipe = Recipe(
                                title = title,
                                ingredients = ingredientsText.split('\n').map { it.trim() }
                                    .filter { it.isNotBlank() },
                                instructions = instructions
                            )
                            viewModel.saveRecipe(newRecipe)
                        } else {
                            viewModel.updateRecipe(recipeId, title, ingredientsText, instructions)
                        }
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (recipeId == -1) "Recept Toevoegen" else "Wijzigingen Opslaan")
            }
        }
    }
}