package com.example.cookbook.data

import com.example.cookbook.data.database.FavoriteRecipeDao
import com.example.cookbook.data.database.RandomRecipeDao
import com.example.cookbook.data.database.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseSource @Inject constructor(
    private val randomRecipeDao: RandomRecipeDao,
    private val favoriteRecipeDao: FavoriteRecipeDao
) {

    fun getAllRecipes(): List<RecipeEntity> {
        return randomRecipeDao.getAllRecipe()
    }

    fun insertAllRecipe(vararg recipes: RecipeEntity) {
        randomRecipeDao.deleteAll()
        randomRecipeDao.insertAllRecipe(*recipes)
    }

    fun getFavoriteRecipes(): List<RecipeEntity> {
        return favoriteRecipeDao.getAllRecipes()
    }

    fun addFavoriteRecipe(recipe: RecipeEntity) {
        favoriteRecipeDao.insertRecipe(recipe)
    }

    fun removeFavoriteRecipe(recipe: RecipeEntity) {
        favoriteRecipeDao.deleteRecipe(recipe.url)
    }
}