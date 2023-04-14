package com.example.cookbook.di.modules

import android.content.Context
import androidx.room.Room
import com.example.cookbook.data.database.AppDatabase
import com.example.cookbook.data.database.RecipeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "new_database")
            .build()
    }

    @Provides
    @Singleton
    fun getRandomRecipeDao(db: AppDatabase): RecipeDao = db.getRandomRecipeDao()
}