package com.example.cookbook.data.repositories_impl

import com.example.cookbook.data.database_sources.DatabaseSource
import com.example.cookbook.data.mappers.DataToEntityRecipeMapper
import com.example.cookbook.data.mappers.EntityToDataRecipeMapper
import com.example.cookbook.data.mappers.ResponseToDataRecipeMapper
import com.example.cookbook.data.network.RecipeService
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val responseToDataMapper: ResponseToDataRecipeMapper,
    private val dataToEntityMapper: DataToEntityRecipeMapper,
    private val entityToDataMapper: EntityToDataRecipeMapper,
    private val service: RecipeService,
    private val database: DatabaseSource
) : RecipeRepository {

    override fun refreshDatabaseWithRandomRecipes(): Completable {
        return service.getRandomRecipeList().flatMapCompletable {
            val list = it.hits?.map { response ->
                responseToDataMapper(response)
            } ?: throw Exception(ERROR_MESSAGE)
            val listOfEntities = list.map { recipe ->
                dataToEntityMapper(recipe)
            }
            database.insertAllRecipes(*listOfEntities.toTypedArray())
            Completable.complete()
        }
    }

    override fun getRecipeListSync(): Observable<List<RecipeData>> {
        return database.getAllRecipesSync().map {
            it.map { entity ->
                entityToDataMapper(entity)
            }
        }
    }

    override fun getRecipeListBySearching(q: String): Single<List<RecipeData>> {
        return service.getRecipeListBySearching(q).map { listResponse ->
            listResponse.hits?.map { itemResponse ->
                responseToDataMapper(itemResponse)
            }
        }
    }

    override fun updateIsFavorite(recipe: RecipeData): Completable {
        return Completable.fromAction {
            database.updateIsFavorite(recipe.isFavorite, recipe.id)
        }
    }

    override fun getFavoriteRecipeList(): Single<List<RecipeData>> {
        return database.getFavoriteRecipes(true).map { listEntity ->
            listEntity.map { entity ->
                entityToDataMapper(entity)
            }
        }
    }

    override fun addFavoriteRecipe(recipe: RecipeData): Completable {
        return Completable.fromAction {
            database.insertAllRecipes(dataToEntityMapper(recipe))
        }
    }

    override fun getFavoriteRecipeListSync(): Observable<List<RecipeData>> {
        return database.getFavoriteRecipesSync(isFavorite = true).map { listEntity ->
            listEntity.map { entity ->
                entityToDataMapper(entity)
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "Service error"
    }
}