package com.example.receptenplanner.di

import android.app.Application
import androidx.room.Room
import com.example.receptenplanner.data.RecipeDao
import com.example.receptenplanner.data.RecipeDatabase
import com.example.receptenplanner.data.ShoppingListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "recipe_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRecipeDao(db: RecipeDatabase): RecipeDao {
        return db.recipeDao()
    }


    @Provides
    fun provideShoppingListDao(db: RecipeDatabase): ShoppingListDao {
        return db.shoppingListDao()
    }


}