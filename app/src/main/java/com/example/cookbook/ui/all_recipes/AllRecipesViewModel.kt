package com.example.cookbook.ui.all_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllRecipesViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    var allRecipes: LiveData<List<RecipeData>>? = null

    private val _isLoadingState = MutableLiveData(true)
    val isLoadingState: LiveData<Boolean>
        get() = _isLoadingState

    init {
        refreshRecipes()
        observeRecipes()
    }

    private fun refreshRecipes() {
        viewModelScope.launch {
            recipeRepository.refreshDatabaseWithRandomRecipes()
        }
    }

    private fun observeRecipes() {
        _isLoadingState.value = true
        viewModelScope.launch {
            allRecipes = recipeRepository.getRecipeListSync()
            _isLoadingState.value = false
        }
    }

    fun updateIsFavorite(recipe: RecipeData) {
        viewModelScope.launch {
            recipeRepository.updateIsFavorite(recipe)
        }
    }
}