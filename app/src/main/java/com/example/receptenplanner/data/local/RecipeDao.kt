package com.example.receptenplanner.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.receptenplanner.models.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe WHERE title LIKE '%' || :query || '%' OR ingredients LIKE '%' || :query || '%' ORDER BY title ASC")
    fun searchRecipes(query: String): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insert(recipe: Recipe)


    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)



    @Query("SELECT * FROM recipe_table WHERE id = :id")
    fun getRecipe(id: Int): Flow<Recipe>


    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    fun getAllRecipes(): Flow<List<Recipe>>
}