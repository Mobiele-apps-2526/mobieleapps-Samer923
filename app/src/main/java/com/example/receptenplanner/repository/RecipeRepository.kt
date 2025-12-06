package com.example.receptenplanner.repository

import com.example.receptenplanner.data.Recipe
import com.example.receptenplanner.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    fun getRecipe(id: Int): Flow<Recipe>
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)


    fun searchRecipes(query: String): Flow<List<Recipe>>


    suspend fun getGroupedShoppingList(recipeIds: List<Int>): List<ShoppingListItem>
}