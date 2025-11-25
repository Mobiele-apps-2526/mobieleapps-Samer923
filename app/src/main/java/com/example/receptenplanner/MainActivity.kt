package com.example.receptenplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.receptenplanner.screen.AddRecipeScreen
import com.example.receptenplanner.screen.Destinations
import com.example.receptenplanner.screen.RecipeDetailScreen
import com.example.receptenplanner.screen.RecipeListScreen
import com.example.receptenplanner.data.RecipeDatabase
import com.example.receptenplanner.repository.RecipeRepository
import com.example.receptenplanner.viewmodel.RecipeViewModel
import com.example.receptenplanner.ui.theme.ReceptenplannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val database = RecipeDatabase.getDatabase(applicationContext)
        val repository = RecipeRepository(database.recipeDao())
        val viewModelFactory = RecipeViewModel.provideFactory(repository)

        setContent {
            ReceptenplannerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    RecipeAppNavigation(viewModelFactory)
                }
            }
        }
    }
}

@Composable
fun RecipeAppNavigation(viewModelFactory: androidx.lifecycle.ViewModelProvider.Factory) {
    val navController = rememberNavController()

    val viewModel: RecipeViewModel = viewModel(factory = viewModelFactory)

    NavHost(navController = navController, startDestination = Destinations.RECIPE_LIST_ROUTE) {

        composable(Destinations.RECIPE_LIST_ROUTE) {
            RecipeListScreen(navController = navController, viewModel = viewModel)
        }

        composable(Destinations.ADD_RECIPE_ROUTE) {
            AddRecipeScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            route = Destinations.RECIPE_DETAIL_ROUTE_WITH_ARG,
            arguments = listOf(navArgument(Destinations.RECIPE_ID_KEY) { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt(Destinations.RECIPE_ID_KEY) ?: return@composable
            RecipeDetailScreen(
                navController = navController,
                viewModel = viewModel,
                recipeId = recipeId
            )
        }
    }
}
