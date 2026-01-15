package com.example.receptenplanner.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.receptenplanner.data.ShoppingListItem
import com.example.receptenplanner.models.model.Recipe

@Database(

    entities = [Recipe::class, ShoppingListItem::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun shoppingListDao(): ShoppingListDao
    companion object {
        @Volatile private var Instance: RecipeDatabase? = null
        fun getDatabase(context: Context): RecipeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecipeDatabase::class.java, "recipe_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}