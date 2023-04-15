package com.example.cookbook.di.modules

import androidx.lifecycle.ViewModel
import com.example.cookbook.di.ViewModelKey
import com.example.cookbook.ui.viewmodels.AllRecipesViewModel
import com.example.cookbook.ui.viewmodels.FavouriteRecipesViewModel
import com.example.cookbook.ui.viewmodels.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindMainViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllRecipesViewModel::class)
    fun bindAllRecipesViewModel(viewModel: AllRecipesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteRecipesViewModel::class)
    fun bindFavouriteRecipesViewModel(viewModel: FavouriteRecipesViewModel): ViewModel
}