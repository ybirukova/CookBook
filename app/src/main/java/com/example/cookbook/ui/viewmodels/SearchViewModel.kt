package com.example.cookbook.ui.viewmodels

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

    private val _recipeLiveData = MutableLiveData<List<RecipeData>>()
    val recipeLiveData: LiveData<List<RecipeData>> get() = _recipeLiveData

    private val _favoriteRecipeLiveData = MutableLiveData<List<RecipeData>>()
    val favoriteRecipeLiveData: LiveData<List<RecipeData>> get() = _favoriteRecipeLiveData

    private val _searchResultLiveData = MutableLiveData<List<RecipeData>>()
    val searchResultLiveData: LiveData<List<RecipeData>> get() = _searchResultLiveData

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _searchLiveData = MutableLiveData<String>()
    val searchLiveData: MutableLiveData<String> get() = _searchLiveData

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
            _favoriteRecipeLiveData.value = recipeRepository.getFavoriteRecipeList()
        }
    }

    fun updateIsFavorite(recipe: RecipeData) {
        viewModelScope.launch {
            recipeRepository.updateIsFavorite(recipe)
            _favoriteRecipeLiveData.value = recipeRepository.getFavoriteRecipeList()
        }
    }
}