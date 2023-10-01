package com.example.cookbook.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipesSync(): Observable<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE isFavorite = :isFavorite")
    fun getFavoriteRecipesSync(isFavorite: Boolean): Observable<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRecipes(recipes: List<RecipeEntity>)

    @Query("UPDATE recipe_table SET isFavorite = :isFavorite WHERE id = :id")
    fun updateIsFavorite(isFavorite: Boolean, id: Int)
}