package com.example.receptenplanner.viewmodel


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewModelScope
import com.example.receptenplanner.data.Recipe
import com.example.receptenplanner.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn;
import kotlinx.coroutines.launch
data class RecipeListUiState(val recipeList: List<Recipe> = listOf())
class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    val recipeListUiState: StateFlow<RecipeListUiState> =
        repository.allRecipes.map { RecipeListUiState(it) }.stateIn(
            scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000), initialValue = RecipeListUiState()
        )
    fun insertRecipe(title: String, ingredients: String, instructions: String) {
        val recipe = Recipe(title = title, ingredients = ingredients, instructions = instructions)
        viewModelScope.launch { repository.insertRecipe(recipe) }
    }
    fun deleteRecipe(recipe: Recipe) { viewModelScope.launch { repository.deleteRecipe(recipe) } }
    fun updateRecipe(id: Int, title: String, ingredients: String, instructions: String) {
        val recipe = Recipe(id = id, title = title, ingredients = ingredients, instructions = instructions)
        viewModelScope.launch { repository.updateRecipe(recipe) }
    }
    fun getRecipe(id: Int): Flow<Recipe> { return repository.getRecipe(id) }
    companion object {
        fun provideFactory(repository: RecipeRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) { return RecipeViewModel(repository) as T }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }
}