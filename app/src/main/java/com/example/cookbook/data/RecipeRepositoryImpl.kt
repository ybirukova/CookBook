package com.example.cookbook.data

import com.example.cookbook.data.mappers.ResponseToDataRecipeMapper
import com.example.cookbook.data.network.RecipeService
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val responseToDataMapper: ResponseToDataRecipeMapper,
    private val service: RecipeService,
    private val database: RandomRecipesDataBaseSource
) : RecipeRepository {

    //получаю список рандомных рецептов (нужно ли в базу данных?)
    override suspend fun getRandomRecipeList(): List<RecipeData> {
        return withContext(Dispatchers.IO) {
            val list = service.getRandomRecipeList().execute()
                .body()?.hits?.map {
                    responseToDataMapper(it)
                } ?: throw Exception()
            list
        }
    }

    override suspend fun getRecipeListBySearching(q: String): List<RecipeData> {
        return withContext(Dispatchers.IO) {
            service.getRecipeListBySearching(q).execute()
                .body()?.hits?.map { responseToDataMapper(it) }
                ?: throw Exception()
        }
    }
}