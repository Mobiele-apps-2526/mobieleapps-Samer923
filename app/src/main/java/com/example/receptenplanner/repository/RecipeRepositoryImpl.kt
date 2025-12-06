package com.example.receptenplanner.repository

import com.example.receptenplanner.data.Recipe
import com.example.receptenplanner.data.RecipeDao
import com.example.receptenplanner.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override fun getAllRecipes(): Flow<List<Recipe>> = recipeDao.getAllRecipes()


    override fun searchRecipes(query: String): Flow<List<Recipe>> = recipeDao.searchRecipes(query)


    override suspend fun getGroupedShoppingList(recipeIds: List<Int>): List<ShoppingListItem> {
        return emptyList()
    }
}