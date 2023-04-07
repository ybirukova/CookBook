package com.example.cookbook.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RandomRecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAllRecipe(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRecipe(vararg recipes: RecipeEntity)

    @Query("DELETE FROM recipe_table")
    fun deleteAll()
}