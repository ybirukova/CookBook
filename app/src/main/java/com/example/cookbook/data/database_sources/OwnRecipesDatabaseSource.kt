package com.example.cookbook.data.database_sources

import androidx.lifecycle.LiveData
import com.example.cookbook.data.database.OwnRecipeDao
import com.example.cookbook.data.database.OwnRecipeEntity
import javax.inject.Inject

class OwnRecipesDatabaseSource @Inject constructor(
    private val ownRecipeDao: OwnRecipeDao,
) {

    fun getAllRecipes(): List<OwnRecipeEntity> {
        return ownRecipeDao.getAllRecipes()
    }

    fun getAllRecipesSync(): LiveData<List<OwnRecipeEntity>> {
        return ownRecipeDao.getAllRecipesSync()
    }

    fun insertAllRecipes(vararg recipes: OwnRecipeEntity) {
        ownRecipeDao.insertAllRecipes(*recipes)
    }

    fun deleteRecipe(id: Int) {
        ownRecipeDao.deleteRecipe(id)
    }
}