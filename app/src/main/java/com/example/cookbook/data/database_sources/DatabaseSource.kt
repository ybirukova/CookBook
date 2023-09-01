package com.example.cookbook.data.database_sources

import com.example.cookbook.data.database.RecipeDao
import com.example.cookbook.data.database.RecipeEntity
import io.reactivex.Observable
import javax.inject.Inject

class DatabaseSource @Inject constructor(
    private val recipeDao: RecipeDao
) {

    fun getAllRecipesSync(): Observable<List<RecipeEntity>> {
        return recipeDao.getAllRecipesSync()
    }

    fun insertAllRecipes(recipes: List<RecipeEntity>) {
        return recipeDao.insertAllRecipes(recipes)
    }

    fun getFavoriteRecipesSync(isFavorite: Boolean): Observable<List<RecipeEntity>> {
        return recipeDao.getFavoriteRecipesSync(isFavorite)
    }

    fun updateIsFavorite(isFavorite: Boolean, id: Int) {
        recipeDao.updateIsFavorite(isFavorite, id)
    }
}