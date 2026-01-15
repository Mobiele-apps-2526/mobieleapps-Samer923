package com.example.receptenplanner.ui.theme.components

sealed class Screen(val route: String) {
    data object Recipes : Screen("recipes")
    data object ShoppingList : Screen("shopping_list")
    data object RecipeDetail : Screen("recipe_detail")
}