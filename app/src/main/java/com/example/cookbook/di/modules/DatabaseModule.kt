package com.example.cookbook.di.modules

import android.content.Context
import androidx.room.Room
import com.example.cookbook.data.database.AppDatabase
import com.example.cookbook.data.database.FavoriteRecipeDao
import com.example.cookbook.data.database.RandomRecipeDao
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
    fun getRandomRecipeDao(db: AppDatabase): RandomRecipeDao = db.getRandomRecipeDao()

    @Provides
    @Singleton
    fun getFavoriteRecipeDao(db: AppDatabase): FavoriteRecipeDao = db.getFavoriteRecipeDao()
}