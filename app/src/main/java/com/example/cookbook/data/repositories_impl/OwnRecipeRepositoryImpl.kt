package com.example.cookbook.data.repositories_impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cookbook.data.database_sources.OwnRecipesDatabaseSource
import com.example.cookbook.data.mappers.DataToOwnEntityRecipeMapper
import com.example.cookbook.data.mappers.OwnEntityToDataRecipeMapper
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.OwnRecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OwnRecipeRepositoryImpl @Inject constructor(
    private val dataToEntityMapper: DataToOwnEntityRecipeMapper,
    private val entityToDataMapper: OwnEntityToDataRecipeMapper,
    private val database: OwnRecipesDatabaseSource
) : OwnRecipeRepository {

    override suspend fun getRecipeList(): List<RecipeData> {
        return withContext(Dispatchers.IO) {
            database.getAllRecipes().map { entityToDataMapper(it) }
        }
    }

    override suspend fun getRecipeListSync(): LiveData<List<RecipeData>> {
        return Transformations.map(database.getAllRecipesSync()) {
            it.map { entity ->
                entityToDataMapper(entity)
            }
        }
    }

    override suspend fun addNewRecipe(recipe: RecipeData) {
        return withContext(Dispatchers.IO) {
            val entity = dataToEntityMapper(recipe)
            database.insertAllRecipes(entity)
        }
    }

    override suspend fun deleteRecipe(id: Int) {
        return withContext(Dispatchers.IO) {
            database.deleteRecipe(id)
        }
    }
}