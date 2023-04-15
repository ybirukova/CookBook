package com.example.cookbook.domain.repository

import com.example.cookbook.domain.models.RecipeData

interface RecipeRepository {

    suspend fun refreshDatabaseWithRandomRecipes()

    suspend fun getRecipeList(): List<RecipeData>

    suspend fun getRecipeListBySearching(q: String): List<RecipeData>

    suspend fun updateIsFavorite(recipe: RecipeData)

    suspend fun getFavoriteRecipeList(): List<RecipeData>
}