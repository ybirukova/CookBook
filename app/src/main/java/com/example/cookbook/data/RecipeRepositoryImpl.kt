package com.example.cookbook.data

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
            val list = service.getRandomRecipeList().hits?.map {
                responseToDataMapper(it)
            } ?: throw Exception()
            val listOfEntities = list.map {
                dataToEntityMapper(it)
            }
            database.insertAllRecipe(*listOfEntities.toTypedArray())
        }
    }

    override suspend fun getRecipeList(): List<RecipeData> {
        return withContext(Dispatchers.IO) {
            val list = database.getAllRecipes().map { entityToDataMapper(it) }
            list
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
}