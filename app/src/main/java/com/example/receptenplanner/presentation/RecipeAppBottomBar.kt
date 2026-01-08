package com.example.receptenplanner.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RecipeAppBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(Screen.Recipes, Screen.ShoppingList)

    NavigationBar {
        items.forEach { screen ->
            val isSelected = currentRoute.startsWith(screen.route)
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Recipes -> Icons.Default.List
                            Screen.ShoppingList -> Icons.Default.ShoppingCart
                            else -> Icons.Default.List
                        },
                        contentDescription = screen.route
                    )
                },
                label = {
                    Text(text = when (screen) {
                        Screen.Recipes -> "Recepten"
                        Screen.ShoppingList -> "Boodschappen"
                        else -> ""
                    })
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigate(screen.route)
                    }
                }
            )
        }
    }
}