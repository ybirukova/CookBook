package com.example.cookbook.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipesSync(): Observable<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE isFavorite = :isFavorite")
    fun getFavoriteRecipes(isFavorite: Boolean): Single<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE isFavorite = :isFavorite")
    fun getFavoriteRecipesSync(isFavorite: Boolean): Observable<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRecipes(vararg recipes: RecipeEntity)

    @Query("UPDATE recipe_table SET isFavorite = :isFavorite WHERE id = :id")
    fun updateIsFavorite(isFavorite: Boolean, id: Int)
}