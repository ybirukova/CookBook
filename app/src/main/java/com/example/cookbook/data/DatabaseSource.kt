package com.example.cookbook.data

import com.example.cookbook.data.database.RecipeDao
import com.example.cookbook.data.database.RecipeEntity
import javax.inject.Inject

class DatabaseSource @Inject constructor(
    private val recipeDao: RecipeDao,
) {

    fun getAllRecipes(): List<RecipeEntity> {
        return recipeDao.getAllRecipe()
    }

    fun insertAllRecipe(vararg recipes: RecipeEntity) {
        recipeDao.deleteAll()
        recipeDao.insertAllRecipe(*recipes)
    }

    fun getFavoriteRecipes(isFavorite: Boolean): List<RecipeEntity> {
        return recipeDao.getFavoriteRecipes(isFavorite)
    }

    fun updateIsFavorite(isFavorite: Boolean, id: Int) {
        recipeDao.updateIsFavorite(isFavorite, id)
    }
}