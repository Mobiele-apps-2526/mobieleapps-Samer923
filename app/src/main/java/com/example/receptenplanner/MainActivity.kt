package com.example.receptenplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.receptenplanner.screen.ShoppingListScreen
import com.example.receptenplanner.screen.recipes.RecipeDetailScreen
import com.example.receptenplanner.screen.recipes.RecipeListScreen
import com.example.receptenplanner.ui.theme.ReceptenplannerTheme
import com.example.receptenplanner.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Recipes : Screen("recipes", "Recepten", Icons.Filled.MenuBook)
    object ShoppingList : Screen("shoppinglist", "Boodschappen", Icons.Filled.List)
    object Detail : Screen("detail/{recipeId}", "Detail", Icons.Filled.MenuBook) {
        fun createRoute(recipeId: Int) = "detail/$recipeId"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceptenplannerTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val viewModel: RecipeViewModel = hiltViewModel()

    val navItems = listOf(Screen.Recipes, Screen.ShoppingList)

    Scaffold(

        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute = navBackStackEntry?.destination?.route

            if (currentRoute != Screen.Detail.route) {
                NavigationBar(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    val currentDestination = navBackStackEntry?.destination

                    navItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },

                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {

                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    launchSingleTop = true

                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Recipes.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Recipes.route) {
                RecipeListScreen(
                    viewModel = viewModel,
                    onNavigateToDetail = { recipeId ->
                        navController.navigate(Screen.Detail.createRoute(recipeId))
                    }
                )
            }


            composable(Screen.ShoppingList.route) {
                ShoppingListScreen(viewModel = viewModel)
            }


            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1

                RecipeDetailScreen(
                    recipeId = recipeId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}