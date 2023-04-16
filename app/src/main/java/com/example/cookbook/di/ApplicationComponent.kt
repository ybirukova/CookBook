package com.example.cookbook.di

import android.content.Context
import com.example.cookbook.di.modules.DatabaseModule
import com.example.cookbook.di.modules.NetworkModule
import com.example.cookbook.di.modules.RepositoryModule
import com.example.cookbook.di.modules.ViewModelModule
import com.example.cookbook.ui.fragments.CreateRecipeFragment
import com.example.cookbook.ui.fragments.FullRecipeFragment
import com.example.cookbook.ui.fragments.SearchFragment
import com.example.cookbook.ui.fragments_menu.AllRecipesFragment
import com.example.cookbook.ui.fragments_menu.FavoriteRecipesFragment
import com.example.cookbook.ui.fragments_menu.OwnRecipesFragment
import com.example.cookbook.ui.fragments_menu.RecipesListBaseFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, RepositoryModule::class, ViewModelModule::class, DatabaseModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(fragment: SearchFragment)

    fun inject(fragment: AllRecipesFragment)

    fun inject(fragment: FavoriteRecipesFragment)

    fun inject(fragment: RecipesListBaseFragment)

    fun inject(fragment: FullRecipeFragment)

    fun inject(fragment: CreateRecipeFragment)

    fun inject(fragment: OwnRecipesFragment)
}