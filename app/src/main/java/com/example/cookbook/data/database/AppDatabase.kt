package com.example.cookbook.data.database

import androidx.room.Database

@Database(entities = [RecipeEntity::class], version = 1)
abstract class AppDatabase {

    abstract fun getRecipeDao(): RandomRecipeDao
}