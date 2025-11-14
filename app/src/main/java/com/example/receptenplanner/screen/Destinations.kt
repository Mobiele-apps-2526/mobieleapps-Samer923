package com.example.receptenplanner.screen

object Destinations {
    const val RECIPE_ID_KEY = "recipeId"

    const val RECIPE_LIST_ROUTE = "recipeList"

    const val ADD_RECIPE_ROUTE = "addRecipe"


    const val RECIPE_DETAIL_ROUTE = "recipeDetail"
    const val RECIPE_DETAIL_ROUTE_WITH_ARG = "$RECIPE_DETAIL_ROUTE/{$RECIPE_ID_KEY}"
}