package com.example.cookbook.di.modules

import com.example.cookbook.data.RecipeRepositoryImpl
import com.example.cookbook.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindRep(impl: RecipeRepositoryImpl): RecipeRepository
}