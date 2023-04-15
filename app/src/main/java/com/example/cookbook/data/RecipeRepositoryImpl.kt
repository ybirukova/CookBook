package com.example.cookbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cookbook.data.mappers.DataToEntityRecipeMapper
import com.example.cookbook.data.mappers.EntityToDataRecipeMapper
import com.example.cookbook.data.mappers.ResponseToDataRecipeMapper
import com.example.cookbook.data.network.RecipeService
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val responseToDataMapper: ResponseToDataRecipeMapper,
    private val dataToEntityMapper: DataToEntityRecipeMapper,
    private val entityToDataMapper: EntityToDataRecipeMapper,
    private val service: RecipeService,
    private val database: DatabaseSource
) : RecipeRepository {

    override suspend fun refreshDatabaseWithRandomRecipes() {
        withContext(Dispatchers.IO) {
            if (database.getAllRecipes().isEmpty()) {
                val list = service.getRandomRecipeList().hits?.map {
                    responseToDataMapper(it)
                } ?: throw Exception()
                val listOfEntities = list.map {
                    dataToEntityMapper(it)
                }
                database.insertAllRecipes(*listOfEntities.toTypedArray())
            }
        }
    }

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

    override suspend fun getRecipeListBySearching(q: String): List<RecipeData> {
        return withContext(Dispatchers.IO) {
            service.getRecipeListBySearching(q).hits?.map {
                responseToDataMapper(it)
            } ?: throw Exception()
        }
    }

    override suspend fun updateIsFavorite(recipe: RecipeData) {
        withContext(Dispatchers.IO) {
            database.updateIsFavorite(recipe.isFavorite, recipe.id)
        }
    }

    override suspend fun getFavoriteRecipeList(): List<RecipeData> {
        return withContext(Dispatchers.IO) {
            val list = database.getFavoriteRecipes(true).map { entityToDataMapper(it) }
            list
        }
    }

    override suspend fun addFavoriteRecipe(recipe: RecipeData) {
        return withContext(Dispatchers.IO) {
            val entity = dataToEntityMapper(recipe)
            database.insertAllRecipes(entity)
        }
    }

    override suspend fun getFavoriteRecipeListSync(): LiveData<List<RecipeData>> {
        return Transformations.map(database.getFavoriteRecipesSync(isFavorite = true)) {
            it.map { entity ->
                entityToDataMapper(entity)
            }
        }
    }
}