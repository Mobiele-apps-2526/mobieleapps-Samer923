package com.example.receptenplanner.repository

import com.example.receptenplanner.data.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    fun getAllItems(): Flow<List<ShoppingListItem>>
    suspend fun insertItem(name: String)
    suspend fun updateItem(item: ShoppingListItem)
    suspend fun deleteItem(item: ShoppingListItem)
    suspend fun clearList()


    suspend fun insertItemsFromRecipe(ingredients: List<String>)
}