package com.example.cookbook.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecipeEntity::class, OwnRecipeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRandomRecipeDao(): RecipeDao

    abstract fun getOwnRecipeDao(): OwnRecipeDao
}