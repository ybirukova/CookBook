package com.example.cookbook.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "own_recipe_table")
data class OwnRecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("mealType") val mealType: String,
    @ColumnInfo("ingredients") val ingredients: String,
    @ColumnInfo("totalTime") val totalTime: String,
    @ColumnInfo("isOwnRecipe") val isOwnRecipe: Boolean
)