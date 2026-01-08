package com.example.receptenplanner.di

import com.example.receptenplanner.repository.RecipeRepository
import com.example.receptenplanner.repository.RecipeRepositoryImpl
import com.example.receptenplanner.repository.ShoppingListRepository
import com.example.receptenplanner.repository.ShoppingListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository


    @Binds
    @Singleton
    abstract fun bindShoppingListRepository(
        shoppingListRepositoryImpl: ShoppingListRepositoryImpl
    ): ShoppingListRepository
}