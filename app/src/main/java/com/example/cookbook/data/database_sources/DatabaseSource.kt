package com.example.cookbook.data.database_sources

import androidx.lifecycle.LiveData
import com.example.cookbook.data.database.RecipeDao
import com.example.cookbook.data.database.RecipeEntity
import javax.inject.Inject

class DatabaseSource @Inject constructor(
    private val recipeDao: RecipeDao
) {

    fun getAllRecipesSync(): LiveData<List<RecipeEntity>> {
        return recipeDao.getAllRecipesSync()
    }

    fun insertAllRecipes(vararg recipes: RecipeEntity) {
        recipeDao.insertAllRecipes(*recipes)
    }

    fun getFavoriteRecipesSync(isFavorite: Boolean): LiveData<List<RecipeEntity>> {
        return recipeDao.getFavoriteRecipesSync(isFavorite)
    }

    fun updateIsFavorite(isFavorite: Boolean, id: Int) {
        recipeDao.updateIsFavorite(isFavorite, id)
    }
}