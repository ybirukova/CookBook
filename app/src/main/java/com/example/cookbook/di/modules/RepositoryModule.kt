package com.example.cookbook.di.modules

import com.example.cookbook.data.repositories_impl.OwnRecipeRepositoryImpl
import com.example.cookbook.data.repositories_impl.RecipeRepositoryImpl
import com.example.cookbook.domain.repository.OwnRecipeRepository
import com.example.cookbook.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindRecipeRep(impl: RecipeRepositoryImpl): RecipeRepository

    @Binds
    fun bindOwnRecipeRep(impl: OwnRecipeRepositoryImpl): OwnRecipeRepository
}