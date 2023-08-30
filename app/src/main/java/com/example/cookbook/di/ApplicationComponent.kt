package com.example.cookbook.di

import android.content.Context
import com.example.cookbook.di.modules.AppModule
import com.example.cookbook.di.modules.DatabaseModule
import com.example.cookbook.di.modules.NetworkModule
import com.example.cookbook.di.modules.RepositoryModule
import com.example.cookbook.di.modules.ViewModelModule
import com.example.cookbook.ui.all_recipes.AllRecipesFragment
import com.example.cookbook.ui.all_recipes.FullRecipeFragment
import com.example.cookbook.ui.create_recipe.CreateRecipeFragment
import com.example.cookbook.ui.create_recipe.OwnRecipesFragment
import com.example.cookbook.ui.create_recipe.OwnRecipesViewModel
import com.example.cookbook.ui.favorite_recipes.FavoriteRecipesFragment
import com.example.cookbook.ui.search_recipes.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        AppModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(fragment: SearchFragment)

    fun inject(fragment: AllRecipesFragment)

    fun inject(fragment: FavoriteRecipesFragment)

    fun inject(fragment: FullRecipeFragment)

    fun inject(fragment: CreateRecipeFragment)

    fun inject(fragment: OwnRecipesFragment)

    fun inject(viewModel: OwnRecipesViewModel)
}