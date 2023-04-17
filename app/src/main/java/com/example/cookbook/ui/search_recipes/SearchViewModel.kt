package com.example.cookbook.ui.search_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbook.domain.models.RecipeData
import com.example.cookbook.domain.repository.RecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val recipeRepository: RecipeRepository) :
    ViewModel() {

    private val _searchResultLiveData = MutableLiveData<List<RecipeData>>()
    val searchResultLiveData: LiveData<List<RecipeData>> get() = _searchResultLiveData

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun searchRecipes(q: String) {
        viewModelScope.launch {
            val list = recipeRepository.getRecipeListBySearching(q)
            _searchResultLiveData.value = list
            _isLoading.value = false
        }
    }

    fun addFavoriteRecipe(recipe: RecipeData) {
        viewModelScope.launch {
            recipeRepository.addFavoriteRecipe(recipe)
        }
    }

    fun updateIsFavorite(recipe: RecipeData) {
        viewModelScope.launch {
            recipeRepository.updateIsFavorite(recipe)
        }
    }
}