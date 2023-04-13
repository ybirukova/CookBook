package com.example.cookbook.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteRecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipes(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipe_table WHERE url = :url")
    fun deleteRecipe(url: String)
}