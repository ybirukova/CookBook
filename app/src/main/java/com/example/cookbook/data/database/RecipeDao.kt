package com.example.cookbook.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipe(): List<RecipeEntity>

    @Query("SELECT * FROM recipe_table WHERE isFavorite = :isFavorite")
    fun getFavoriteRecipes(isFavorite: Boolean): List<RecipeEntity>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRecipe(vararg recipes: RecipeEntity)

    @Query("DELETE FROM recipe_table WHERE isFavorite = false")
    fun deleteAll()

    @Query("UPDATE recipe_table SET isFavorite = :isFavorite WHERE id = :id")
    fun updateIsFavorite(isFavorite: Boolean, id: Int)
}