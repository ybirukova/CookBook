package com.example.cookbook.di.modules

import androidx.lifecycle.ViewModel
import com.example.cookbook.di.ViewModelKey
import com.example.cookbook.ui.RecipeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    fun bindMainViewModel(viewModel: RecipeViewModel): ViewModel
}