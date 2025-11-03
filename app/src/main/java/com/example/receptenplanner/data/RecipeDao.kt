package com.example.receptenplanner.data
import androidx.room.Dao; import androidx.room.Delete; import androidx.room.Insert
import androidx.room.OnConflictStrategy; import androidx.room.Query; import androidx.room.Update
import kotlinx.coroutines.flow.Flow
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun insert(recipe: Recipe)
    @Update suspend fun update(recipe: Recipe)
    @Delete suspend fun delete(recipe: Recipe)
    @Query("SELECT * FROM recipe_table WHERE id = :id") fun getRecipe(id: Int): Flow<Recipe>
    @Query("SELECT * FROM recipe_table ORDER BY title ASC") fun getAllRecipes(): Flow<List<Recipe>>
}