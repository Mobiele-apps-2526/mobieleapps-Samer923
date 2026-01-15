package com.example.receptenplanner.data.repository

import com.example.receptenplanner.models.model.Recipe
import com.example.receptenplanner.data.local.RecipeDao
import com.example.receptenplanner.models.model.ShoppingListItem
import com.example.receptenplanner.models.model.repository.RecipeRepository
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