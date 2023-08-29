package com.example.cookbook.data.database_sources

import com.example.cookbook.data.database.OwnRecipeDao
import com.example.cookbook.data.database.OwnRecipeEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class OwnRecipesDatabaseSource @Inject constructor(
    private val ownRecipeDao: OwnRecipeDao,
) {

    fun getAllRecipes(): Single<List<OwnRecipeEntity>> {
        return ownRecipeDao.getAllRecipes()
    }

    fun getAllRecipesSync(): Observable<List<OwnRecipeEntity>> {
        return ownRecipeDao.getAllRecipesSync()
    }

    fun insertAllRecipes(vararg recipes: OwnRecipeEntity) {
        ownRecipeDao.insertAllRecipes(*recipes)
    }

    fun deleteRecipe(id: Int) {
        ownRecipeDao.deleteRecipe(id)
    }
}