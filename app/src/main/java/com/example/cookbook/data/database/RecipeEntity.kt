package com.example.cookbook.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo("label") val label: String,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("mealType") val mealType: String,
    @ColumnInfo("ingredientLines") val ingredientLines: String,
    @ColumnInfo("totalTime") val totalTime: String,
    @ColumnInfo("isFavorite") val isFavorite: Boolean
)
