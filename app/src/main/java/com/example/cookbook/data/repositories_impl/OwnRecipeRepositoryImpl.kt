package com.example.cookbook.data.repositories_impl

import com.example.cookbook.data.database_sources.OwnRecipesDatabaseSource
import com.example.cookbook.data.mappers.DataToOwnEntityRecipeMapper
import com.example.cookbook.data.mappers.OwnEntityToDataRecipeMapper
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.OwnRecipeRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class OwnRecipeRepositoryImpl @Inject constructor(
    private val dataToEntityMapper: DataToOwnEntityRecipeMapper,
    private val entityToDataMapper: OwnEntityToDataRecipeMapper,
    private val database: OwnRecipesDatabaseSource
) : OwnRecipeRepository {

    override fun getRecipeList(): Single<List<RecipeData>> {
        return database.getAllRecipes().map {
            val list = it.map { entity ->
                entityToDataMapper(entity)
            }
            list
        }
    }

    override fun getRecipeListSync(): Observable<List<RecipeData>> {
        val list = database.getAllRecipesSync().map {
            it.map { entity ->
                entityToDataMapper(entity)
            }
        }
        return list
    }

    override fun addNewRecipe(recipe: RecipeData): Single<Unit> {
        return Single.just(recipe)
            .map { dataToEntityMapper(it) }
            .flatMapCompletable { entity ->
                Completable.fromAction {
                    database.insertAllRecipes(entity)
                }
            }
            .toSingleDefault(Unit)
    }

    override fun deleteRecipe(id: Int): Single<Unit> {
        return Single.just(id)
            .flatMapCompletable { id ->
                Completable.fromAction {
                    database.deleteRecipe(id)
                }
            }
            .toSingleDefault(Unit)
    }
}