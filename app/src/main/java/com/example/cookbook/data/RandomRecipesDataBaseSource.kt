package com.example.cookbook.data

import com.example.cookbook.data.database.RandomRecipeDao
import com.example.cookbook.data.database.RecipeEntity
import javax.inject.Inject

class RandomRecipesDataBaseSource @Inject constructor(private val recipeDao: RandomRecipeDao) {

    fun getAllRecipes(): List<RecipeEntity> {
        return recipeDao.getAllRecipe()
    }

    fun insertAllRecipe(vararg recipes: RecipeEntity) {
        recipeDao.deleteAll()
        recipeDao.insertAllRecipe(*recipes)
    }
}