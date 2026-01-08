package com.example.receptenplanner.repository

import com.example.receptenplanner.data.ShoppingListDao
import com.example.receptenplanner.data.ShoppingListItem
import com.example.receptenplanner.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepositoryImpl @Inject constructor(
    private val dao: ShoppingListDao
) : ShoppingListRepository {

    override fun getAllItems(): Flow<List<ShoppingListItem>> = dao.getAllItems()

    override suspend fun insertItem(name: String) {
        val newItem = ShoppingListItem(name = name)
        dao.insertItem(newItem)
    }

    override suspend fun updateItem(item: ShoppingListItem) {
        dao.updateItem(item)
    }

    override suspend fun deleteItem(item: ShoppingListItem) {
        dao.deleteItem(item)
    }

    override suspend fun clearList() {
        dao.clearList()
    }


    override suspend fun insertItemsFromRecipe(ingredients: List<String>) {
        ingredients.forEach { ingredient ->

            if (ingredient.isNotBlank()) {
                val newItem = ShoppingListItem(name = ingredient.trim())
                dao.insertItem(newItem)
            }
        }
    }
}