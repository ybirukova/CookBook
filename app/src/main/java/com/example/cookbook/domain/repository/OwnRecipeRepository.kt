package com.example.cookbook.domain.repository

import androidx.lifecycle.LiveData
import com.example.cookbook.domain.models.RecipeData

interface OwnRecipeRepository {

    suspend fun getRecipeList(): List<RecipeData>

    suspend fun getRecipeListSync(): LiveData<List<RecipeData>>

    suspend fun addNewRecipe(recipe: RecipeData)

    suspend fun deleteRecipe(id: Int)
}