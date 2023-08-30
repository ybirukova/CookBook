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
        return database.getAllRecipes().map { entityList ->
            entityList.map { entity ->
                entityToDataMapper(entity)
            }
        }
    }

    override fun getRecipeListSync(): Observable<List<RecipeData>> {
        return database.getAllRecipesSync().map {
            it.map { entity ->
                entityToDataMapper(entity)
            }
        }
    }

    override fun addNewRecipe(recipe: RecipeData): Completable {
        return Completable.fromAction {
            database.insertAllRecipes(dataToEntityMapper(recipe))
        }
    }

    override fun deleteRecipe(id: Int): Completable {
        return Completable.fromAction {
            database.deleteRecipe(id)
        }
    }
}