package com.example.cookbook.ui.viewmodels

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

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _searchResultLiveData = MutableLiveData<List<RecipeData>>()
    val searchResultLiveData: LiveData<List<RecipeData>>
        get() = _searchResultLiveData

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
        _isLoading.value = true
        viewModelScope.launch {
            allRecipes = recipeRepository.getRecipeListSync()
            _isLoading.value = false
        }
    }

    fun updateIsFavorite(recipe: RecipeData) {
        viewModelScope.launch {
            recipeRepository.updateIsFavorite(recipe)
        }
    }
}