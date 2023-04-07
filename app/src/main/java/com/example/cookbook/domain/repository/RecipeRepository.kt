package com.example.cookbook.domain.repository

import com.example.cookbook.domain.models.RecipeData

interface RecipeRepository {

    suspend fun getRandomRecipeList(): List<RecipeData>

    suspend fun getRecipeListBySearching(q: String): List<RecipeData>
}