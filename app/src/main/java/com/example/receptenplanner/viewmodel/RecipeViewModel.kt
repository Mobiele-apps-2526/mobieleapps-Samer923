package com.example.receptenplanner.viewmodel




import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptenplanner.models.model.Recipe
import com.example.receptenplanner.models.model.repository.RecipeRepository
import com.example.receptenplanner.models.model.repository.ShoppingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val shoppingListRepository: ShoppingListRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _recipes = recipeRepository.getAllRecipes()

    val filteredRecipes: StateFlow<List<Recipe>> = _recipes
        .combine(searchQuery) { recipes, query ->
            if (query.isBlank()) {
                recipes
            } else {
                recipes.filter {
                    it.title.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun saveRecipe(recipe: Recipe) = viewModelScope.launch {
        recipeRepository.insertRecipe(recipe)
    }

    fun updateRecipe(id: Int, title: String, ingredientsText: String, instructions: String) = viewModelScope.launch {
        val ingredientsList = ingredientsText.split('\n')
            .map { it.trim() }
            .filter { it.isNotBlank() }

        val updatedRecipe = Recipe(
            id = id,
            title = title,
            ingredients = ingredientsList,
            instructions = instructions
        )
        recipeRepository.updateRecipe(updatedRecipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        recipeRepository.deleteRecipe(recipe)
    }

    fun getRecipe(id: Int): Flow<Recipe?> {
        return recipeRepository.getRecipe(id)
    }

    fun addRecipeIngredientsToShoppingList(ingredients: List<String>) = viewModelScope.launch {
        shoppingListRepository.insertItemsFromRecipe(ingredients)
    }

    val shoppingListItems = shoppingListRepository.getAllItems()

    fun insertShoppingListItem(name: String) = viewModelScope.launch {
        shoppingListRepository.insertItem(name)
    }

    fun updateShoppingListItem(item: com.example.receptenplanner.data.ShoppingListItem) = viewModelScope.launch {
        shoppingListRepository.updateItem(item)
    }

    fun deleteShoppingListItem(item: com.example.receptenplanner.data.ShoppingListItem) = viewModelScope.launch {
        shoppingListRepository.deleteItem(item)
    }

    fun clearShoppingList() = viewModelScope.launch {
        shoppingListRepository.clearList()
    }
}