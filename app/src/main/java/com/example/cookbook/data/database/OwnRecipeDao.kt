package com.example.cookbook.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface OwnRecipeDao {

    @Query("SELECT * FROM own_recipe_table")
    fun getAllRecipes(): Single<List<OwnRecipeEntity>>

    @Query("SELECT * FROM own_recipe_table")
    fun getAllRecipesSync(): Observable<List<OwnRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRecipes(vararg recipes: OwnRecipeEntity)

    @Query("DELETE FROM own_recipe_table WHERE id = :id")
    fun deleteRecipe(id: Int)
}