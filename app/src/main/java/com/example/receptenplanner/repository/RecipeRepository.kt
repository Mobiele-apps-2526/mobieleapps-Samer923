package com.example.receptenplanner.repository

import com.example.receptenplanner.data.Recipe
import com.example.receptenplanner.data.RecipeDao
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {
    val allRecipes: Flow<List<Recipe>> = recipeDao.getAllRecipes()
    suspend fun insertRecipe(recipe: Recipe) { recipeDao.insert(recipe) }
    suspend fun updateRecipe(recipe: Recipe) { recipeDao.update(recipe) }
    suspend fun deleteRecipe(recipe: Recipe) { recipeDao.delete(recipe) }
    fun getRecipe(id: Int): Flow<Recipe> { return recipeDao.getRecipe(id) }
}