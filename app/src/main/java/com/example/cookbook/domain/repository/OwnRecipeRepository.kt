package com.example.cookbook.domain.repository

import com.example.cookbook.domain.models.RecipeData
import io.reactivex.Observable
import io.reactivex.Single

interface OwnRecipeRepository {

    fun getRecipeList(): Single<List<RecipeData>>

    fun getRecipeListSync(): Observable<List<RecipeData>>

    fun addNewRecipe(recipe: RecipeData): Single<Unit>

    fun deleteRecipe(id: Int): Single<Unit>
}