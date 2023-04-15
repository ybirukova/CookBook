package com.example.cookbook.domain.repository

import androidx.lifecycle.LiveData
import com.example.cookbook.domain.models.RecipeData

interface RecipeRepository {

    suspend fun refreshDatabaseWithRandomRecipes()

    suspend fun getRecipeList(): List<RecipeData>

    suspend fun getRecipeListSync(): LiveData<List<RecipeData>>

    suspend fun getRecipeListBySearching(q: String): List<RecipeData>

    suspend fun updateIsFavorite(recipe: RecipeData)

    suspend fun getFavoriteRecipeList(): List<RecipeData>

    suspend fun addFavoriteRecipe(recipe: RecipeData)

    suspend fun getFavoriteRecipeListSync(): LiveData<List<RecipeData>>
}