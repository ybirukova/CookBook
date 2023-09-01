package com.example.cookbook.domain.repository

import com.example.cookbook.domain.models.RecipeData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface RecipeRepository {

    fun refreshDatabaseWithRandomRecipes(): Completable

    fun getRecipeListSync(): Observable<List<RecipeData>>

    fun getRecipeListBySearching(q: String): Single<List<RecipeData>>

    fun getOwnRecipeListBySearching(title: String): Single<List<RecipeData>>

    fun updateIsFavorite(recipe: RecipeData): Completable

    fun addFavoriteRecipe(recipe: RecipeData): Completable

    fun getFavoriteRecipeListSync(): Observable<List<RecipeData>>
}