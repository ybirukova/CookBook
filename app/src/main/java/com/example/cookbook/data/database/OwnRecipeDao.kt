package com.example.cookbook.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OwnRecipeDao {

    @Query("SELECT * FROM own_recipe_table")
    fun getAllRecipes(): List<OwnRecipeEntity>

    @Query("SELECT * FROM own_recipe_table")
    fun getAllRecipesSync(): LiveData<List<OwnRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRecipes(vararg recipes: OwnRecipeEntity)

    @Query("DELETE FROM recipe_table WHERE id = :id")
    fun deleteRecipe(id: Int)
}